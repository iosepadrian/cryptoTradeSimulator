package com.example.myapplication.data.data.model

import java.io.Serializable

class Category(
    val id:Int,
    val name:String,
    val subcategoryList: List<SubCategory>
):Serializable {
    override fun toString(): String {
        return "Category(id=$id, name='$name', subcategoryList=$subcategoryList)"
    }
}