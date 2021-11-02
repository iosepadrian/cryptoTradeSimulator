package com.example.myapplication.data.data.model

import java.io.Serializable

class SubCategory(
    val name:String,
    val description:String,
    val price:Float,
    val image:String
):Serializable {
    override fun toString(): String {
        return "SubCategory(name='$name', description='$description')"
    }
}
