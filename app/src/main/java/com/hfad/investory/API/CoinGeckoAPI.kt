package com.hfad.investory.API

import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoAPI {
    @GET("coins/markets")
    suspend fun getTopCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): List<CryptoCoin>
}