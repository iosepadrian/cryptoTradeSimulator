package com.example.myapplication.data.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity
class Transaction(
    @PrimaryKey @Nonnull val id:Int,
    @ColumnInfo(name = "id_user") val id_user:String,
    @ColumnInfo(name = "id_coin") val id_coin:String,
    @ColumnInfo(name = "date") val date:String,
    @ColumnInfo(name = "from") val from:String,
    @ColumnInfo(name = "to") val to:String,
    @ColumnInfo(name = "fromValue") val fromValue:Double,
    @ColumnInfo(name = "toValue") val toValue:Double

)
{
    override fun toString(): String {
        return "Transaction(id=$id, id_user='$id_user', id_coin='$id_coin', date='$date', from='$from', to='$to', fromValue=$fromValue, toValue=$toValue)"
    }
}