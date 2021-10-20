package com.example.myapplication.data.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.model.Favcoin
import com.example.myapplication.repository.database.FavCoinDatabase
import kotlinx.android.synthetic.main.fragment_deatil.view.*

class FavCoinAdapter(private val coinList: List<Favcoin>) : RecyclerView.Adapter<FavCoinAdapter.ViewHolder>() {
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



        val coin= coinList[position]

        holder.nume.text=coin.name

        holder.img.setOnClickListener {
            val db= FavCoinDatabase.getDatabase(holder.img.context)
            val favcoin= Favcoin(coin.id,coin.name)
            db.favCoinDao().delete(favcoin)
        }

    }

    override fun getItemCount(): Int {
        return coinList.size
    }
}