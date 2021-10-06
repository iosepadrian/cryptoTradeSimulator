package com.example.myapplication.repository

import com.example.myapplication.api.ApiHelper
import com.example.myapplication.api.ApiService
import com.example.myapplication.api.SafeApiRequest
import javax.inject.Inject

class CoinRepository(private val apiHelper: ApiService) :SafeApiRequest(){

    suspend fun getCoins()= apiRequest { apiHelper.getCoins() }
}