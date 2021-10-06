package com.example.myapplication.data.model

class Coin (
    val nume:String,
    val nameAbbrev:String,
    val percentage:String
        ){
    override fun toString(): String {
        return "Coin(nume='$nume', nameAbbrev='$nameAbbrev', percentage='$percentage')"
    }
}