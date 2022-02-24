package com.example.myapplication.data.data.model

import androidx.room.ColumnInfo
import com.example.myapplication.data.data.model.buy.MarketData
import com.google.gson.annotations.SerializedName

class CoinTopDetails (
    @SerializedName("id") val id : String,
    @SerializedName("symbol") val symbol : String,
    @SerializedName("name") val name : String,
    @SerializedName("categories") val categories : List<String>,
    @SerializedName("image") val image : Image,
    @SerializedName("description") val description : Description,
    @SerializedName("links") val links : Links,
    @SerializedName("coingecko_rank") val coingecko_rank : String,
    @SerializedName("market_cap_rank") val market_cap_rank : String,
    @SerializedName("market_data") val marketData: MarketData

) {
    override fun toString(): String {
        return "CoinTopDetails(id='$id', image=$image, coingecko_rank='$coingecko_rank')"
    }
}