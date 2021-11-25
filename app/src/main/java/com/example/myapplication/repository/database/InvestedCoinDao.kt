package com.example.myapplication.repository.database

import androidx.room.*
import com.example.myapplication.data.data.model.Favcoin
import com.example.myapplication.data.data.model.InvestedCoin
import kotlinx.coroutines.flow.Flow


@Dao
interface InvestedCoinDao {
    @Query("SELECT * FROM investedcoin")
    fun load(): InvestedCoin

    @Query("SELECT * FROM investedcoin")
    fun loadAsync(): Flow<InvestedCoin>

    @Query("SELECT * FROM investedcoin")
    fun loadAll(): Flow<List<InvestedCoin>>

    @Query("SELECT * FROM investedcoin")
    fun loadAllNoSync():List<InvestedCoin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: InvestedCoin)

    @Delete
    fun delete(user: InvestedCoin?)
}