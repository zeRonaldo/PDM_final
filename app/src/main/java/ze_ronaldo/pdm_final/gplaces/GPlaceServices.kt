package ze_ronaldo.pdm_final.gplaces

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GPlaceServices {

    private val interceptor by lazy {
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

    }
    private val client by lazy {
        //OkHttpClient.Builder().addInterceptor(interceptor).build()
        OkHttpClient.Builder().build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(PlaceApi.BASE_API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val gPlacesApi by lazy {
        retrofit.create(PlaceApi::class.java)
    }

    /**
     * Makes a request.
     */
    fun request() = gPlacesApi
}