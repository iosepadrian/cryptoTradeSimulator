package com.example.myapplication.repository

import android.app.Application
import android.util.Log
import com.example.myapplication.data.data.model.Favcoin
import com.example.myapplication.repository.database.FavCoinDao
import com.example.myapplication.repository.database.FavCoinDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavCoinRepository @Inject constructor(application: Application) {
    private var favCoinDao: FavCoinDao

    init {
        val db = FavCoinDatabase.getDatabase(application)
        favCoinDao = db.favCoinDao()
    }

    fun getFavCoinAsync(): Flow<Favcoin> {
        return favCoinDao.loadAsync()
    }

    fun getAllFavCoins():Flow<List<Favcoin>>{
        return favCoinDao.loadAll()
    }

    fun insert(user : Favcoin) {
        print(Log.v("AdiTag","favorite inserted"))
        favCoinDao.insert(user)
    }

    fun delete(user: Favcoin?){
        print(Log.v("AdiTag","favorite deleted"))
        favCoinDao.delete(user)
    }
}