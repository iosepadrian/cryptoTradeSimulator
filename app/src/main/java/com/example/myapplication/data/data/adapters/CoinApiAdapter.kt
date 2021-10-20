package com.example.myapplication.data.data.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.data.model.CoinApi
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_deatil.view.*
import java.util.*

class CoinApiAdapter(
) : RecyclerView.Adapter<CoinApiAdapter.DataViewHolder>(),Filterable {

    private lateinit var mListener: onItemClickListener
    private lateinit var coins: List<CoinApi>
    private lateinit var coinsFiltered: List<CoinApi>

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener=listener


    }


    inner class DataViewHolder(itemView:View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        var price: TextView
        var name: TextView
        var img:ImageView
        var totalvolume:Chip
        init {
            name = itemView.findViewById(R.id.apicoinname)
            price=itemView.findViewById(R.id.apicoinprice)
            img=itemView.findViewById(R.id.coinimg)
            totalvolume=itemView.findViewById(R.id.totalvolumechip)
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }


        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.api_coin_layout, parent, false)
        return DataViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val coin= coinsFiltered[position]
        holder.name.text=coin.name
        holder.price.text="$ "+ String.format("%.10f",coin.current_price)
        //holder.price.text=coin.current_price.toString()
        holder.totalvolume.text=coin.total_volume
        Glide.with(holder.img.context).load(coin.image).into(holder.img)
    }

    override fun getItemCount(): Int {
        return coinsFiltered.size


    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<CoinApi>) {
        coins = list
        coinsFiltered = coins
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) coinsFiltered = coins else {
                    val filteredList = ArrayList<CoinApi>()
                    coins
                        .filter {
                            (it.id.contains(constraint!!)) or
                                    (it.id.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    coinsFiltered = filteredList.toList()
                }
                return FilterResults().apply { values = coinsFiltered }
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                coinsFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as List<CoinApi>
                notifyDataSetChanged()
            }
        }
    }

    fun returnData(): List<CoinApi> {
        return coinsFiltered
    }

}
