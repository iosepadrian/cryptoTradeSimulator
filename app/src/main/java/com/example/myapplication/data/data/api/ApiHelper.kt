package com.example.myapplication.data.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getCoins() = apiService.getCoins()
}