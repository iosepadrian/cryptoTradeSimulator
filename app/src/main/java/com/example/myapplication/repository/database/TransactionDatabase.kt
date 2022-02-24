package com.example.myapplication.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.data.model.Transaction

@Database(entities = [Transaction::class],version = 2)
abstract class TransactionDatabase: RoomDatabase() {

    abstract fun TransactionDao(): TransactionDao

    companion object {

        private var INSTANCE: TransactionDatabase? = null

        fun getDatabase(context: Context): TransactionDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TransactionDatabase::class.java,
                        "DatabaseTran"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as TransactionDatabase
        }
    }


}