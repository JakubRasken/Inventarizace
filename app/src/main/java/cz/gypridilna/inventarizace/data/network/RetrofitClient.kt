package cz.gypridilna.inventarizace.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbzOZH0mQ2A51ZNoXaKgpI37wlv75qkrsy6vVzdzxRtuf8Agkq08WyvdOjD0axc2xAI/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
