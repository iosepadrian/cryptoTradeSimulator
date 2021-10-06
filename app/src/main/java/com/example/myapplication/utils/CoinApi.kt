package com.example.myapplication.utils

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
     val name:String
        )
{
    override fun toString(): String {
        return "CoinApi(id='$id', symbol='$symbol', name='$name')"
    }
}