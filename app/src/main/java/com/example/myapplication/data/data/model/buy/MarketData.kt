package com.example.myapplication.data.data.model.buy

import com.google.gson.annotations.SerializedName

data class MarketData (
    @SerializedName("current_price") val current_price : CurrentPrice,
)