package com.hfad.investory.API

import com.google.gson.annotations.SerializedName

data class CryptoCoin(
    val image: String,
    val symbol: String,
    @SerializedName("price_change_percentage_24h") val priceChange: Double,
    @SerializedName("current_price") val currentPrice: Double
)