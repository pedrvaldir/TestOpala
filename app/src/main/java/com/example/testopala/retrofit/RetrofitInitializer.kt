package com.example.testopala.retrofit

import com.example.testopala.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.URL.BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun padService(): PadService {
        return retrofit.create(PadService::class.java)
    }
}
