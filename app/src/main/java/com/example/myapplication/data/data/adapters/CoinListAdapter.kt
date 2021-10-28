package com.example.myapplication.data.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.model.Coin

class CoinListAdapter(private val coinList: List<Coin>) :RecyclerView.Adapter<CoinListAdapter.ViewHolder>(){



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nume: TextView
        var abrev: TextView
        var img: ImageView

        init {
            nume = itemView.findViewById(R.id.coinName)
            abrev = itemView.findViewById(R.id.coinAbbreviation)
            img=itemView.findViewById(R.id.coinImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val coin= coinList[position]

        holder.nume.text=coin.nume
        holder.abrev.text=coin.nameAbbrev
        when(coin.nameAbbrev){
            "BTC" -> holder.img.setImageResource(R.drawable.btc)
            "ETH" -> holder.img.setImageResource(R.drawable.eth)
            "SOL" -> holder.img.setImageResource(R.drawable.sol)
        }
    }

    override fun getItemCount(): Int {
        return coinList.size
    }


}


