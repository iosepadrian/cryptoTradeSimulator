package com.example.myapplication.data.data.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
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

    private lateinit var mListener: onItemClickListener
    private lateinit var coins: List<Favcoin>
    private lateinit var coinsFiltered: List<Favcoin>

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener=listener


    }
    inner class ViewHolder(itemView: View,listener: FavCoinAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var nume: TextView
        var img:ImageView

        init {
            nume = itemView.findViewById(R.id.namefavcoin)
            img=itemView.findViewById(R.id.orangefavimg)
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCoinAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_card_view, parent, false)
        return ViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: FavCoinAdapter.ViewHolder, position: Int) {



        val coin= coinsFiltered[position]

        holder.nume.text=coin.name

        holder.img.setOnClickListener {

            val builder = AlertDialog.Builder(holder.img.context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val db= FavCoinDatabase.getDatabase(holder.img.context)
                    val favcoin= Favcoin(coin.id,coin.name,coin.symbol,coin.image,coin.rank)
                    db.favCoinDao().delete(favcoin)
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()

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