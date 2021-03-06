package ze_ronaldo.pdm_final.gplaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ze_ronaldo.pdm_final.gplaces.pojos.GPlaceResult
import ze_ronaldo.pdm_final.gplaces.pojos.GPlaceSearchResults

interface PlaceApi {
    companion object {
        const val BASE_API_URL = "https://maps.googleapis.com"
    }

    @GET("/maps/api/place/nearbysearch/json")
    fun search(
            @Query("location") location: String,
//            @Query("minprice") minprice: Int,
//            @Query("maxprice") maxprice: Int,
            @Query("radius") radius: Int,
            @Query("type") type: String,
            @Query("price_level") priceLevel: Int,
            @Query("key") apiKey: String
    ): Call<GPlaceSearchResults>


    @GET("/maps/api/place/details/json") // Ajustar URL
    fun getPlaceById(
        @Query("placeid") placeId: String,
        @Query("key") apiKey: String
    ): Call<GPlaceResult> // Lembrar de ajustar o resultado

//    @GET("/maps/api/place/photo")
//    fun getImg(
//        @Query("maxwidth") maxWidth: Int,
//        @Query("photoreference") photoRef:String,
//        @Query("key") apiKey:String
//    ): Call<Bitmap>
}