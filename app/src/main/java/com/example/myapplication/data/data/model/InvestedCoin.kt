package com.example.myapplication.data.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity
class InvestedCoin
    (
    @PrimaryKey @Nonnull val id:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "symbol") val symbol:String,
    @ColumnInfo(name = "image") val image:String,
    @ColumnInfo(name = "marketcap_rank") val rank:String
            )
{
    override fun toString(): String {
        return "InvestedCoin(id='$id', name='$name', symbol='$symbol', image='$image', rank='$rank')"
    }
}