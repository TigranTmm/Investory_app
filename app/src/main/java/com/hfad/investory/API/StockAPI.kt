package com.hfad.investory.API

import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {
    @GET("stock-screener")
    suspend fun getTopStocks(
        @Query("marketCapMoreThan") marketCapMin: Long = 10_000_000_000,
        @Query("limit") limit: Int = 100,
        @Query("apikey") apikey: String
    ) : List<StockCoins>
}