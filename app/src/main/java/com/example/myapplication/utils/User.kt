package com.example.myapplication.utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity
data class User  (
    @PrimaryKey @Nonnull val id: String,
    @ColumnInfo(name = "password") val password:String?,
    @ColumnInfo(name = "username") val username:String?

    ) {

    override fun toString(): String {
        return "User(id='$id', password=$password, username=$username)"
    }

}