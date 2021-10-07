package com.example.myapplication.repository

import com.example.myapplication.data.data.api.ApiService
import com.example.myapplication.data.data.api.SafeApiRequest

class CoinDetailsRepository(private val apiHelper: ApiService) : SafeApiRequest() {

    suspend fun getCoinDetails(id:String)= apiRequest { apiHelper.getCoinDetails(id) }

    suspend fun getCoinTopDetails(id:String)=apiRequest { apiHelper.getCoinTopDetails(id) }
}