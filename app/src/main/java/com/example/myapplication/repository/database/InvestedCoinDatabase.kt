package com.example.myapplication.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.data.model.Favcoin
import com.example.myapplication.data.data.model.InvestedCoin

@Database(entities = [InvestedCoin::class],version = 2)
abstract class InvestedCoinDatabase: RoomDatabase() {

    abstract fun investedCoinDao(): InvestedCoinDao

    companion object {

        private var INSTANCE: InvestedCoinDatabase? = null

        fun getDatabase(context: Context): InvestedCoinDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        InvestedCoinDatabase::class.java,
                        "DatabaseInvested"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as InvestedCoinDatabase
        }
    }


}