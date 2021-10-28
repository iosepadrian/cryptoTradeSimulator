package com.example.myapplication.data.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.data.model.Coin
import com.example.myapplication.data.data.model.Favcoin
import kotlinx.android.synthetic.main.coin_card_view.view.*
import kotlinx.android.synthetic.main.fragment_deatil.view.*


class CoinsListAdapter(): ListAdapter<Favcoin, CoinsListAdapter.CoinsViewHolder>(CoinDiffCallback()) {
    private lateinit var mListener: CoinsListAdapter.onItemClickListener


    fun setOnItemClickListener(listener: CoinsListAdapter.onItemClickListener) {

        mListener=listener


    }
    fun returnData(): List<Favcoin> {
        return currentList
    }
    class CoinsViewHolder(itemView: View,listener: CoinsListAdapter.onItemClickListener): RecyclerView.ViewHolder(itemView) {
        var nume: TextView
        var abrev: TextView
        var img: ImageView
        var rank: TextView

        init {
            nume = itemView.coinName
            abrev = itemView.coinAbbreviation
            img=itemView.findViewById(R.id.coinImageView)
            rank=itemView.rankCoin
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }


        fun bind(coin: Favcoin) {
            nume.text=coin.name
            abrev.text=coin.symbol
            rank.text="#"+coin.rank
            Glide.with(img.context).load(coin.image).into(img)

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_card_view, parent, false)
        return CoinsViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }




    class CoinDiffCallback: DiffUtil.ItemCallback<Favcoin>(){
        override fun areItemsTheSame(oldItem: Favcoin, newItem: Favcoin): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Favcoin, newItem: Favcoin): Boolean {
            return areItemsTheSame(oldItem,newItem)
        }
    }
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
}





