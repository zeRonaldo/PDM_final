package ze_ronaldo.pdm_final.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_place.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ze_ronaldo.pdm_final.R
import ze_ronaldo.pdm_final.extensions.loadUrl
import ze_ronaldo.pdm_final.gplaces.GPlaceServices
import ze_ronaldo.pdm_final.gplaces.pojos.GPlaceResult
import ze_ronaldo.pdm_final.models.Place
import ze_ronaldo.pdm_final.room.PlaceDatabase


class PlaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        val place = intent.extras?.getSerializable(PLACE_EXTRA) as? Place
        if (place != null) {
            showPlace(place)
            requestPlaceById(place.placeId)
        } else {
            showError("Lugar nulo ou sem informação de ID")
            return
        }
    }

    private fun showPlace(place: Place) {
        println(place.toString())
        tvPlaceName.text = place.name
        tvPlaceAddress.text = place.address
        rbRating.rating = place.rating
        tvRating.text = place.rating.toString()

        callToPlace(place)
        goToPlace(place)

        RefreshFavoriteAsyncTask().execute(place)

        btFave.setOnClickListener {
            ToggleFavoriteAsyncTask().execute(place)
        }

        if (place.telephoneNumber != null) {
            btCallPlace.text = getString(R.string.place_call_to)
        } else {
            btCallPlace.visibility = View.GONE
        }

        btGoPlace.text = getString(R.string.place_navigate_to, place.name)

        place.firstImage?.let { imageUrl ->
            imgBGRestaurant.loadUrl(
                getString(
                    R.string.gmaps_image_url,
                    "400",
                    imageUrl,
                    getString(R.string.general_gplace_api)
                )
            )
        }
        place.secondImage?.let { imguRL ->
            imgFGRestaurant.loadUrl(
                getString(
                    R.string.gmaps_image_url, "200", imguRL, getString(R.string.general_gplace_api)
                )
            )
        }

    }

    private fun callToPlace(place: Place) {
        btCallPlace.setOnClickListener {
            if (place.telephoneNumber != null) {
                val uri = Uri.parse("tel:${place.telephoneNumber}")
                val intent = Intent(Intent.ACTION_DIAL, uri)
                startActivity(intent)
            } else {
                showErrorWithoutClose("Não foi encontrado um Telefone Válido")
            }
        }
    }

    private fun goToPlace(place: Place) {
        val origin = "-7.1356496,-34.8760932"
        val destination = "${place.latitude},${place.longitude}"
        val url = "http://maps.google.com/maps"
        val uri = Uri.parse("${url}?f=&saddr=${origin}+&daddr=${destination}")

        btGoPlace.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    private fun requestPlaceById(placeId: String) {
        println("Request $placeId")

        GPlaceServices
            .request()
            .getPlaceById(
                placeId = placeId,
                apiKey = getString(R.string.general_gplace_api)
            )
            .enqueue(object : Callback<GPlaceResult> {
                override fun onFailure(call: Call<GPlaceResult>, t: Throwable) {
                    showError("Requisição para o Google Place teve um retorno inexperado")
                }

                override fun onResponse(
                    call: Call<GPlaceResult>,
                    response: Response<GPlaceResult>
                ) {
                    if (response.isSuccessful) {
                        println("SUCCESS")
                        response.body()?.result?.let { placeNew ->
                            showPlace(placeNew.toPlace())
                        }
                    }
                }
            })
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun showErrorWithoutClose(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun refreshFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            btFave.setImageDrawable(
                ContextCompat.getDrawable(
                    this@PlaceActivity,
                    R.drawable.ic_favorite_black_24dp
                )
            )
        } else {
            btFave.setImageDrawable(
                ContextCompat.getDrawable(
                    this@PlaceActivity,
                    R.drawable.ic_favorite_border_black_24dp
                )
            )
        }
    }

    companion object {
        private const val PLACE_EXTRA = "place"

        @JvmStatic
        fun getCallingIntent(context: Context, place: Place): Intent {
            return Intent(context, PlaceActivity::class.java).apply {
                putExtra(PLACE_EXTRA, place)
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class RefreshFavoriteAsyncTask : AsyncTask<Place, Void, Boolean>() {
        private val placesDao = PlaceDatabase.getInstance(this@PlaceActivity)?.placeDAO()

        override fun doInBackground(vararg params: Place): Boolean {
            val place = params.firstOrNull()

            return if (place != null) {
                placesDao?.findByID(place.placeId) != null
            } else {
                false
            }
        }

        override fun onPostExecute(isFavorite: Boolean) {
            refreshFavoriteButton(isFavorite)
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class ToggleFavoriteAsyncTask : AsyncTask<Place, Void, Boolean>() {
        private val placesDao = PlaceDatabase.getInstance(this@PlaceActivity)?.placeDAO()

        override fun doInBackground(vararg params: Place): Boolean {
            val place = params.firstOrNull()
            if (place != null) {
                val isFavorite = placesDao?.findByID(place.placeId) != null

                if (isFavorite)
                    placesDao?.delete(place)
                else
                    placesDao?.insertPlace(place)

                println("Favorites ===========")
                placesDao?.getAll()?.forEach { favoritePlace ->
                    println("Favorite! ${favoritePlace.name}")
                }
                println("===========")

                return !isFavorite
            } else {
                return false
            }
        }

        override fun onPostExecute(isFavorite: Boolean) {
            refreshFavoriteButton(isFavorite)
        }
    }
}
