package com.example.myapplication.data.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinDetails (
    @SerializedName("prices")
    @Expose
    val prices : MutableList<List<String>>,

){
    override fun toString(): String {
        return "CoinDetails(prices=$prices)"
    }
}