package com.example.myapplication.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.data.data.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun load(): User

    @Query("SELECT * FROM user")
    fun loadAsync(): Flow<User>

    @Query("SELECT * FROM user")
    fun loadAll():List<User>

    @Insert(onConflict = REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User?)

}