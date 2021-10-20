package com.example.myapplication.data.data.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.model.CoinDetails
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class CoinDetailsAdapter(
    private val details: CoinDetails
): RecyclerView.Adapter<CoinDetailsAdapter.DetailViewHolder>() {


    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time: TextView
        var price: TextView
        var diffpercentage:TextView
        var updownimage:ImageView

        init {
            time = itemView.findViewById(R.id.time)
            price = itemView.findViewById(R.id.price)
            diffpercentage=itemView.findViewById(R.id.differencepercentage)
            updownimage=itemView.findViewById(R.id.updownprice)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_card_view, parent, false)
        return DetailViewHolder(v)
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        if(position!=0) {
            val coin = details.prices[position]
            val coin2 = details.prices[position-1]
            val date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(coin[0].toLong()),
                ZoneId.systemDefault()
            )

            val d1=coin[1].toFloat()
            val d2=coin2[1].toFloat()
            val diferenta= String.format("%.6f",((d1.minus(d2)).div(d2)).times(100))
            if(diferenta.take(1)=="-"){
                val list=diferenta.split(",",".",ignoreCase = false)
                holder.diffpercentage.text=list[0].subSequence(1,list[0].length).toString() + "." + list[1].take(1)+"%"
                holder.diffpercentage.setTextColor(Color.RED)
                holder.updownimage.setImageResource(R.drawable.downprice)
            }
            else
            {
                val list=diferenta.split(",",".",ignoreCase = false)
                holder.diffpercentage.text=list[0].subSequence(0,list[0].length).toString() + "." + list[1].take(1)+"%"
                holder.diffpercentage.setTextColor(Color.parseColor("#006d77"))
                holder.updownimage.setImageResource(R.drawable.upprice)
            }
            holder.time.text =
                date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
            holder.price.text = "$ " + coin[1]
        }
        else{
            val coin = details.prices[position]
            val date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(coin[0].toLong()),
                ZoneId.systemDefault())
            holder.time.text =
                date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
            holder.price.text = "$ " + coin[1]
            holder.diffpercentage.setTextColor(Color.parseColor("#006d77"))
        }


    }
    fun deleteItem(index: Int){
        details.prices.removeAt(index)
        notifyDataSetChanged()

    }
    override fun getItemCount(): Int {
        return details.prices.size
    }

}