package com.example.myapplication.repository

import android.app.Application
import android.util.Log
import com.example.myapplication.data.data.model.InvestedCoin
import com.example.myapplication.repository.database.InvestedCoinDao
import com.example.myapplication.repository.database.InvestedCoinDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvestedCoinRepository @Inject constructor(application: Application) {

    private var investedCoinDao: InvestedCoinDao

    init {
        val db = InvestedCoinDatabase.getDatabase(application)
        investedCoinDao = db.investedCoinDao()
    }

    fun getFavCoinAsync(): Flow<InvestedCoin> {
        return investedCoinDao.loadAsync()
    }

    fun getAllInvestedCoins(): Flow<List<InvestedCoin>> {
        return investedCoinDao.loadAll()
    }

    fun insert(user : InvestedCoin) {
        print(Log.v("AdiTag","invested inserted"))
        investedCoinDao.insert(user)
    }

    fun delete(user: InvestedCoin?){
        print(Log.v("AdiTag","invested deleted"))
        investedCoinDao.delete(user)
    }

}