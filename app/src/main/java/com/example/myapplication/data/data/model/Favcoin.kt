package com.example.myapplication.data.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity
class Favcoin(
    @PrimaryKey @Nonnull val id:String,
    @ColumnInfo(name = "name") val name:String
) {
    override fun toString(): String {
        return "FavCoin(id='$id', name='$name')"
    }
}