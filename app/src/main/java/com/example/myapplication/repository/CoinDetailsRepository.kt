package com.example.myapplication.repository

import com.example.myapplication.data.data.api.ApiService
import com.example.myapplication.data.data.api.SafeApiRequest
import com.example.myapplication.repository.database.FavCoinDao
import com.example.myapplication.repository.database.FavCoinDatabase

class CoinDetailsRepository(private val apiHelper: ApiService) : SafeApiRequest() {

    suspend fun getCoinDetails(id:String)= apiRequest { apiHelper.getCoinDetails(id) }

    suspend fun getCoinDetailsForOneDay(id:String)= apiRequest { apiHelper.getCoinDetailsForOneDay(id) }

    suspend fun getCoinDetailsForTwoDays(id:String)= apiRequest { apiHelper.getCoinDetailsForTwoDays(id) }

    suspend fun getCoinTopDetails(id:String)=apiRequest { apiHelper.getCoinTopDetails(id) }


}