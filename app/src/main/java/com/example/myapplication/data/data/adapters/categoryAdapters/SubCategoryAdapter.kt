package com.example.myapplication.data.data.adapters.categoryAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.CoinApiAdapter
import com.example.myapplication.data.data.adapters.categoryAdapters.SubCategoryAdapter.*
import com.example.myapplication.data.data.model.SubCategory
import com.google.android.material.chip.Chip
import android.graphics.Typeface

import android.util.TypedValue
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.example.myapplication.data.data.model.Image
import kotlinx.android.synthetic.main.subcategory_card_view.view.*
import java.lang.String


class SubCategoryAdapter(
    var sebSelectionInterface: SubSelectionInterface,
    var subcategoylist:ArrayList<SubCategory>,
    var clickedPosition:Int
): RecyclerView.Adapter<MyViewHolder>() {



    interface SubSelectionInterface {
        fun onsubselection(position: Int)
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var description: TextView
        var img:ImageView
        var row:CardView
        init {
            name = itemView.findViewById(R.id.subcategoryname)
            description = itemView.findViewById(R.id.subcategorydescription)
            row=itemView.findViewById(R.id.subCategoryCardView)
            img=itemView.subcategoryimage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.subcategory_card_view, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = subcategoylist?.get(position)!!.name
        holder.description.text = subcategoylist?.get(position)!!.description
        holder.img.setImageResource(R.drawable.food)



        holder.row.setOnClickListener(View.OnClickListener {
            if (clickedPosition !== position) {
                clickedPosition = position
                sebSelectionInterface.onsubselection(position)
                notifyDataSetChanged()
            }
        })
    }

    override fun getItemCount(): Int {
        return subcategoylist.size
    }
}