package com.example.myapplication.data.data.model.buy

import com.google.gson.annotations.SerializedName

data class CurrentPrice (
    @SerializedName("usd") val usd : Double
    )