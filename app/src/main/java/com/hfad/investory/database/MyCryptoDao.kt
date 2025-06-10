package com.hfad.investory.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyCryptoDao {
    @Query("SELECT * FROM my_crypto WHERE userId = :userId")
    suspend fun getAllByUser(userId: String): List<MyCrypto>

    @Query("SELECT * FROM my_crypto WHERE id = :id")
    suspend fun getById(id: Int): MyCrypto?

    @Insert
    suspend fun insert(coin: MyCrypto)

    @Delete
    suspend fun delete(coin: MyCrypto)

    @Update
    suspend fun update(coin: MyCrypto)
}