package com.example.myapplication.repository

import com.example.myapplication.data.data.api.ApiService
import com.example.myapplication.data.data.api.SafeApiRequest

class CoinRepository(private val apiHelper: ApiService) :SafeApiRequest(){

    suspend fun getCoins()= apiRequest { apiHelper.getCoins() }

}