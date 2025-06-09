package com.hfad.investory.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MyCryptoDao {
    @Query("SELECT * FROM my_crypto WHERE userId = :userId")
    suspend fun getAllByUser(userId: String): List<MyCrypto>

    @Insert
    suspend fun insert(coin: MyCrypto)

    @Delete
    suspend fun delete(coin: MyCrypto)
}