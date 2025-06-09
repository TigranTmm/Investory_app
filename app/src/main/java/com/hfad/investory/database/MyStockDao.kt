package com.hfad.investory.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MyStockDao {
    @Query("SELECT * FROM my_stock WHERE userId = :userId")
    suspend fun getAllByUser(userId: String): List<MyStock>

    @Insert
    suspend fun insert(stock: MyStock)

    @Delete
    suspend fun delete(stock: MyStock)
}
