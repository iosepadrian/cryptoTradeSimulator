/*
package com.example.myapplication.data.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.CoinAdaptor.CoinsViewHolder
import com.example.myapplication.data.data.model.CoinApi
import java.util.*

class CoinAdaptor: ListAdapter<CoinApi, CoinsViewHolder>(CoinDiffCallback()),Filterable {

    var coinList: List<CoinApi>?=null

    var coinsFiltered: List<CoinApi>?=null

    class CoinsViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var id: TextView
        var symbol: TextView
        var name: TextView
        init {
            id = itemView.findViewById(R.id.apicoinid)
            symbol = itemView.findViewById(R.id.apicoinsymbol)
            name = itemView.findViewById(R.id.apicoinname)
        }


        fun bind(current: CoinApi) {
            id.text=current.id
            name.text=current.name
            symbol.text=current.symbol
        }



    }



    fun setData(list: MutableList<CoinApi>?){
        this.coinList = list!!
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.api_coin_layout, parent, false)
        return CoinsViewHolder(v)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
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
    class CoinDiffCallback:DiffUtil.ItemCallback<CoinApi>(){
        override fun areItemsTheSame(oldItem: CoinApi, newItem: CoinApi): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: CoinApi, newItem: CoinApi): Boolean {
            return areItemsTheSame(oldItem,newItem)
        }
    }

}





*/
