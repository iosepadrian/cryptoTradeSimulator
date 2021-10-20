package com.example.myapplication.data.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinApi (
    @SerializedName("id")
    @Expose
     val id:String,
    @SerializedName("symbol")
    @Expose
     val symbol:String,
    @SerializedName("name")
    @Expose
     val name:String,
    @SerializedName("image")
    @Expose
    val image : String,
    @SerializedName("total_volume")
    @Expose
    val total_volume : String,
    @SerializedName("current_price")
    @Expose
    val current_price : Float,
    @SerializedName("market_cap_rank")
    @Expose
    val market_cap_rank : Int
        )

{

    override fun toString(): String {
        return "CoinApi(id='$id', symbol='$symbol', name='$name', image='$image', current_price=$current_price, market_cap_rank=$market_cap_rank)"
    }
}