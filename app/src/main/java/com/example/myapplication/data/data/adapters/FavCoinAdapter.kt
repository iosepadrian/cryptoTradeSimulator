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
import com.example.myapplication.R
import com.example.myapplication.data.data.model.CoinApi
import com.example.myapplication.data.data.model.Favcoin
import com.example.myapplication.repository.database.FavCoinDatabase
import kotlinx.android.synthetic.main.fragment_deatil.view.*
import java.util.ArrayList

class FavCoinAdapter() : RecyclerView.Adapter<FavCoinAdapter.ViewHolder>(), Filterable {

    private lateinit var coins: List<Favcoin>
    private lateinit var coinsFiltered: List<Favcoin>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nume: TextView
        var img:ImageView

        init {
            nume = itemView.findViewById(R.id.namefavcoin)
            img=itemView.findViewById(R.id.orangefavimg)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCoinAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: FavCoinAdapter.ViewHolder, position: Int) {



        val coin= coinsFiltered[position]

        holder.nume.text=coin.name

        holder.img.setOnClickListener {
            val db= FavCoinDatabase.getDatabase(holder.img.context)
            val favcoin= Favcoin(coin.id,coin.name)
            db.favCoinDao().delete(favcoin)
        }

    }

    override fun getItemCount(): Int {
        return coinsFiltered.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(sortedBy: List<Favcoin>) {
        coins = sortedBy
        coinsFiltered = coins
        notifyDataSetChanged()
    }
    fun returnData(): List<Favcoin> {
        return coinsFiltered
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) coinsFiltered = coins else {
                    val filteredList = ArrayList<Favcoin>()
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
                    results.values as List<Favcoin>
                notifyDataSetChanged()
            }
        }
    }
}