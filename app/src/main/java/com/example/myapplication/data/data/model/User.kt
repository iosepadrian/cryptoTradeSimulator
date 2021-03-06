package com.example.myapplication.data.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity
data class User  (
    @PrimaryKey @Nonnull val id: String,
    @ColumnInfo(name = "password") val password:String?,
    @ColumnInfo(name = "username") val username:String?,
    @ColumnInfo(name = "uri") val uri:String?,
    @ColumnInfo(name = "balance") val balance:Float?,
    @ColumnInfo(name = "noOfTransactions") val noOfTransactions:Int?
    ) {


    override fun toString(): String {
        return "User(id='$id', password=$password, username=$username, uri=$uri, balance=$balance, noOfTransactions=$noOfTransactions)"
    }
}