package com.hfad.investory.API

data class StockCoins(
    val symbol: String,
    val name: String,
    val price: Double,
    val change: Double,
    val changesPercentage: String
)
