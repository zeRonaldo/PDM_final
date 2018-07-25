package ze_ronaldo.pdm_final.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ze_ronaldo.pdm_final.R
import ze_ronaldo.pdm_final.R.id.btGo
import ze_ronaldo.pdm_final.gplaces.GPlaceResult
import ze_ronaldo.pdm_final.gplaces.GPlaceServices
import ze_ronaldo.pdm_final.models.Place

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btGo.setOnClickListener {
            requestPlacesAPI()
        }
    }

    private fun requestPlacesAPI() {
        // Exibir loading

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
            .enqueue(object : Callback<GPlaceResult> {
                override fun onFailure(call: Call<GPlaceResult>, t: Throwable) {
                    // oculta o loading
                }

                override fun onResponse(
                    call: Call<GPlaceResult>,
                    response: Response<GPlaceResult>
                ) {
                    // oculta o loading
                    if (response.isSuccessful) {
                        response.body()?.results?.shuffled()?.firstOrNull()?.let { randomPlace ->
                            openPlaceActv(randomPlace)
                        }
                    }
                }
            })

    }

    private fun openPlaceActv(place: Place) {
        startActivity(PlaceActivity.getCallingIntent(this, place))
    }
}
