package com.example.myapplication.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.data.adapters.FavCoinAdapter
import com.example.myapplication.data.data.model.Favcoin

@Database(entities = [Favcoin::class],version = 2)
abstract class FavCoinDatabase: RoomDatabase() {

    abstract fun favCoinDao(): FavCoinDao

    companion object {

        private var INSTANCE: FavCoinDatabase? = null

        fun getDatabase(context: Context): FavCoinDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavCoinDatabase::class.java,
                        "DatabaseFav"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as FavCoinDatabase
        }
    }


}