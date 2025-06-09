package com.hfad.investory.API

import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {
    @GET("stock_market/actives")
    suspend fun getTopStocks(
        @Query("limit") limit: Int = 100,
        @Query("apikey") apikey: String
    ) : List<StockCoins>
}