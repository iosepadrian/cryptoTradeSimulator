package com.example.myapplication.repository.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.data.data.model.Transaction
@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    fun load(): Transaction

    @Query("SELECT * FROM `transaction`")
    fun loadAsync(): Flow<Transaction>

    @Query("SELECT * FROM `transaction`")
    fun loadAll(): Flow<List<Transaction>>

    @Query("SELECT * FROM `transaction`")
    fun loadAllNoSync():List<Transaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tran: Transaction)

    @Delete
    fun delete(tran: Transaction?)
}