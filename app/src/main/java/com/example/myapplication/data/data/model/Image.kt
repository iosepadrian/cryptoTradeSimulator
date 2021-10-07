package com.example.myapplication.data.data.model

import com.google.gson.annotations.SerializedName

data class Image (

    @SerializedName("thumb") val thumb : String,
    @SerializedName("small") val small : String,
    @SerializedName("large") val large : String
)