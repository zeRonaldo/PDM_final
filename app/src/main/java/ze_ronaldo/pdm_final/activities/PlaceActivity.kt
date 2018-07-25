package ze_ronaldo.pdm_final.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_place.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ze_ronaldo.pdm_final.models.Place
import ze_ronaldo.pdm_final.R
import ze_ronaldo.pdm_final.R.id.imgRestaurant
import ze_ronaldo.pdm_final.gplaces.GPlaceResult
import ze_ronaldo.pdm_final.gplaces.GPlaceServices
import android.graphics.BitmapFactory
import com.squareup.picasso.Picasso
import java.net.URL


class PlaceActivity : AppCompatActivity() {

    private lateinit var place: Place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        place = requestPlaceById()

        showPlace()

        btCallPlace.setOnClickListener(){
            callToPlace()
        }
        btGoPlace.setOnClickListener(){
            goToPlace()
        }

    }

    private fun showPlace() {
        println(place.toString())
        tvPlaceName.text = place.name
        tvPlaceAddress.text = place.address
        rbRating.setRating(place.rating)
        tvRating.text = place.rating.toString()
            if (place.telephoneNumber != null) {
                btCallPlace.text = getString(R.string.place_call_to)
            }else{
                btCallPlace.setVisibility(View.GONE)
            }
        btGoPlace.text = getString(R.string.place_navigate_to, place.name)
        loadImg(getString(R.string.loadImageURL,"400", place?.photos!![0].photoRefence,getString(R.string.general_gplace_api)), imgBGRestaurant)
    }
    fun loadImg(url:String, imgView:ImageView){
        Picasso.get().load(url).into(imgView);
    }
    fun callToPlace(){
        if (place.telephoneNumber != null){
            val uri = Uri.parse("tel:${place.telephoneNumber}")
            val it = Intent (Intent.ACTION_DIAL, uri)
            startActivity(it)
        }else{
            showErrorWithoutClose("Não foi encontrado um Telefone Válido")
        }
    }
    fun goToPlace(){
            val origem = "-7.1356496,-34.8760932"
            val destino = "${place.geometry.location.lat},${place.geometry.location.lng}"
            val url = "http://maps.google.com/maps"
            val uri = Uri.parse("${url}?f=&saddr=${origem}+&daddr=${destino}")
            val it = Intent(Intent.ACTION_VIEW, uri)
            startActivity(it)

    }
    private fun requestPlaceById() :Place{
        var place1 = intent.extras?.getSerializable(PLACE_EXTRA) as? Place
        println("entrando no request")
        if (place1 != null) {
            GPlaceServices
                .request()
                .getPlaceById(
                    placeId = place1.placeId,
                    apiKey = getString(R.string.general_gplace_api)
                )
                .enqueue(object : Callback<Place> {
                    override fun onFailure(call: Call<Place>, t: Throwable) {
                       showError("Requisição para o Google Place teve um retorno inexperado")
                    }

                    override fun onResponse(
                        call: Call<Place>,
                        response: Response<Place>
                    ) {
                        if (response.isSuccessful) {
                            println("SUCCESS")
                            response.body()?.let { placeNew ->
                                place1 = placeNew

                            }
                        }
                    }
                })

        }else{

            showError("Lugar nulo ou sem informação de ID")

        }
        return place1!!
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        finish()
    }
    private fun showErrorWithoutClose(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
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
}
