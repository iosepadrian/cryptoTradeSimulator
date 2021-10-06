package com.example.myapplication.repository

import com.example.myapplication.api.ApiService
import com.example.myapplication.api.SafeApiRequest

class CoinDetailsRepository(private val apiHelper: ApiService) : SafeApiRequest() {

    suspend fun getCoinDetails(id:String)= apiRequest { apiHelper.getCoinDetails(id) }

    suspend fun getCoinTopDetails(id:String)=apiRequest { apiHelper.getCoinTopDetails(id) }
}