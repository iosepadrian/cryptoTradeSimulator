package com.example.myapplication.data.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity
class Transaction(
    @PrimaryKey @Nonnull val id:String,
    @ColumnInfo(name = "id_user") val id_user:String,
    @ColumnInfo(name = "id_coin") val id_coin:String,
    @ColumnInfo(name = "date") val date:String

)