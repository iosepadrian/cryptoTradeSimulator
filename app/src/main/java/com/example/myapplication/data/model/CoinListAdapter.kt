package com.example.myapplication.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class CoinListAdapter(private val coinList: List<Coin>) :RecyclerView.Adapter<CoinListAdapter.ViewHolder>(){



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nume: TextView
        var percentage: TextView
        var abrev: TextView
        lateinit var img: ImageView

        init {
            nume = itemView.findViewById(R.id.coinName)
            percentage = itemView.findViewById(R.id.percentageCoin)
            abrev = itemView.findViewById(R.id.coinAbbreviation)
            img=itemView.findViewById(R.id.coinImageView)




           /* itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, DetailPertanyaan::class.java).apply {
                    putExtra("NUMBER", position)
                    putExtra("CODE", itemKode.text)
                    putExtra("CATEGORY", itemKategori.text)
                    putExtra("CONTENT", itemIsi.text)
                }
                context.startActivity(intent)
            }*/
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
        holder.percentage.text=coin.percentage
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


