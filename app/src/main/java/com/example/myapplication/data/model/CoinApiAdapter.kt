package com.example.myapplication.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.utils.CoinApi
import kotlinx.android.synthetic.main.api_coin_layout.view.*
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
        var id: TextView
        var symbol: TextView
        var name: TextView
        init {
            id = itemView.findViewById(R.id.apicoinid)
            symbol = itemView.findViewById(R.id.apicoinsymbol)
            name = itemView.findViewById(R.id.apicoinname)
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }


        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinApiAdapter.DataViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.api_coin_layout, parent, false)
        return DataViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: CoinApiAdapter.DataViewHolder, position: Int) {
        val coin= coinsFiltered[position]

        holder.id.text=coin.id
        holder.name.text=coin.name
        holder.symbol.text=coin.symbol
    }

    override fun getItemCount(): Int {
        return coinsFiltered.size


    }

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
