package ze_ronaldo.pdm_final.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ze_ronaldo.pdm_final.R
import ze_ronaldo.pdm_final.extensions.disable
import ze_ronaldo.pdm_final.extensions.enable
import ze_ronaldo.pdm_final.gplaces.GPlaceServices
import ze_ronaldo.pdm_final.gplaces.pojos.GPlaceSearchResults
import ze_ronaldo.pdm_final.models.Place

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressLoading.visibility = View.INVISIBLE
        btGo.setOnClickListener {
            requestPlacesAPI()
        }
        btFavorites.setOnClickListener { openFavoritesActv() }
    }

    private fun requestPlacesAPI() {
        // Exibir loading
        progressLoading.visibility = View.VISIBLE
        btGo.disable()

        GPlaceServices
            .request()
            .search(
                location = "-7.104338, -34.829803",
//                minprice = 0,
//                maxprice = 4,
                radius = 1000,
                type = "restaurant",
                priceLevel = 2,
                apiKey = getString(R.string.general_gplace_api)
            )
            .enqueue(object : Callback<GPlaceSearchResults> {
                override fun onFailure(call: Call<GPlaceSearchResults>, t: Throwable) {
                    progressLoading.visibility = View.INVISIBLE
                    btGo.enable()
                }

                override fun onResponse(
                    call: Call<GPlaceSearchResults>,
                    response: Response<GPlaceSearchResults>
                ) {
                    progressLoading.visibility = View.INVISIBLE
                    btGo.enable()

                    if (response.isSuccessful) {
                        response.body()?.results?.shuffled()?.firstOrNull()?.let { randomPlace ->
                            openPlaceActv(randomPlace.toPlace())
                        }
                    }
                }
            })

    }

    private fun openPlaceActv(place: Place) {
        startActivity(PlaceActivity.getCallingIntent(this, place))
    }

    private fun openFavoritesActv() {
        startActivity(Intent(this, FavoritesActivity::class.java))
    }
}
