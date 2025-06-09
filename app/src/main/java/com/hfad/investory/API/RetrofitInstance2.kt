package com.hfad.investory.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance2 {
    private const val BASE_URL = "https://financialmodelingprep.com/api/v3/"

    val api: StockAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StockAPI::class.java)
    }
}