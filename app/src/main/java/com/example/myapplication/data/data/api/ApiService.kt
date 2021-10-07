package com.example.myapplication.data.data.api

import com.example.myapplication.data.data.model.CoinApi
import com.example.myapplication.data.data.model.CoinDetails
import com.example.myapplication.data.data.model.CoinTopDetails
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("coins/list")
    suspend fun getCoins():Response<List<CoinApi>>

    @GET("coins/{id}/market_chart?vs_currency=usd&days=7")
    suspend fun getCoinDetails(@Path("id") id:String?):Response<CoinDetails>

    @GET("coins/{id}")
    suspend fun getCoinTopDetails(@Path("id")id:String?):Response<CoinTopDetails>


    companion object{
        operator fun invoke() : ApiService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.coingecko.com/api/v3/")
                .build()
                .create(ApiService::class.java)
        }
    }

}