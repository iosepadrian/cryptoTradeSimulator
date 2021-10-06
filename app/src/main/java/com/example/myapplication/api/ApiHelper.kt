package com.example.myapplication.api

import javax.inject.Inject

class ApiHelper(private val apiService: ApiService) {

    suspend fun getCoins() = apiService.getCoins()
}