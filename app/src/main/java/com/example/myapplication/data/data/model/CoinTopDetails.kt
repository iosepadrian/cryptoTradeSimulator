package com.example.myapplication.data.data.model

import com.google.gson.annotations.SerializedName

class CoinTopDetails (
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("categories") val categories : List<String>,
    @SerializedName("image") val image : Image,
    @SerializedName("description") val description : Description,
    @SerializedName("links") val links : Links,
    @SerializedName("coingecko_rank") val coingecko_rank : String


) {
    override fun toString(): String {
        return "CoinTopDetails(id='$id', image=$image, coingecko_rank='$coingecko_rank')"
    }
}