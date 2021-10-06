package com.example.myapplication.api

import com.example.myapplication.utils.CoinApi
import com.example.myapplication.utils.CoinDetails
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("coins/list")
    suspend fun getCoins():Response<List<CoinApi>>

    @GET("coins/{id}/market_chart?vs_currency=usd&days=7")
    suspend fun getCoinDetails(@Path("id") id:String?):Response<CoinDetails>


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