package com.example.myapplication.data.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.model.Coin
import kotlinx.android.synthetic.main.coin_card_view.view.*


class CoinsListAdapter: ListAdapter<Coin, CoinsListAdapter.CoinsViewHolder>(CoinDiffCallback()) {


    class CoinsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nume: TextView
        var percentage: TextView
        var abrev: TextView
        var img: ImageView

        init {
            nume = itemView.coinName
            percentage = itemView.percentageCoin
            abrev = itemView.coinAbbreviation
            img=itemView.findViewById(R.id.coinImageView)
        }


        fun bind(coin: Coin) {
            nume.text=coin.nume
            abrev.text=coin.nameAbbrev
            percentage.text=coin.percentage
            when(coin.nameAbbrev){
                "BTC" -> img.setImageResource(R.drawable.btc)
                "ETH" -> img.setImageResource(R.drawable.eth)
                "SOL" -> img.setImageResource(R.drawable.sol)
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_card_view, parent, false)
        return CoinsViewHolder(v)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }




    class CoinDiffCallback: DiffUtil.ItemCallback<Coin>(){
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.nume==newItem.nume
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return areItemsTheSame(oldItem,newItem)
        }
    }

}





