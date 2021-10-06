package com.example.myapplication.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinDetails (
    @SerializedName("prices")
    @Expose
    val prices : List<List<String>>,

){
    override fun toString(): String {
        return "CoinDetails(prices=$prices)"
    }
}