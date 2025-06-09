package com.hfad.investory.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_stock")
data class MyStock(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val iconUrl: String,
    val symbol: String,
    val amount: Double,
    val pricePerCoin: Double,
    val totalValue: Double,
    val userId: String
)
