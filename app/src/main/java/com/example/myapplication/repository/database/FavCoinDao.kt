package com.example.myapplication.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.data.data.model.Favcoin
import kotlinx.coroutines.flow.Flow


@Dao
interface FavCoinDao {
    @Query("SELECT * FROM favcoin")
    fun load(): Favcoin

    @Query("SELECT * FROM favcoin")
    fun loadAsync(): Flow<Favcoin>

    @Query("SELECT * FROM favcoin")
    fun loadAll():Flow<List<Favcoin>>

    @Query("SELECT * FROM favcoin")
    fun loadAllNoSync():List<Favcoin>

    @Insert(onConflict = REPLACE)
    fun insert(user: Favcoin)

    @Delete
    fun delete(user: Favcoin?)
}