package com.example.myapplication.data.model

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.utils.CoinApi
import com.example.myapplication.utils.CoinDetails
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class CoinDetailsAdapter(
    private val details: CoinDetails
): RecyclerView.Adapter<CoinDetailsAdapter.DetailViewHolder>() {


    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time: TextView
        var price: TextView

        init {
            time = itemView.findViewById(R.id.time)
            price = itemView.findViewById(R.id.price)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_card_view, parent, false)
        return DetailViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val coin = details.prices[position]
        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(coin[0].toLong()), ZoneId.systemDefault())

        holder.time.text = date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
        holder.price.text = coin[1]
    }

    override fun getItemCount(): Int {
        return details.prices.size
    }

}