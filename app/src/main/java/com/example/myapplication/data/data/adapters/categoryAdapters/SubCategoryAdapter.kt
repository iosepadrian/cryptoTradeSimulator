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
import android.util.Log

import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.myapplication.data.data.model.Image
import kotlinx.android.synthetic.main.subcategory_card_view.view.*
import java.lang.String


class SubCategoryAdapter(
    var sebSelectionInterface: SubSelectionInterface,
    var subcategoylist:List<SubCategory>,
    var clickedPosition:Int
): RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener=listener


    }
    interface SubSelectionInterface {
        fun onsubselection(position: Int)
    }

    class MyViewHolder(itemView:View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var description: TextView
        var img:ImageView
        var row:CardView
        var pb:ProgressBar
        init {
            name = itemView.findViewById(R.id.subcategoryname)
            description = itemView.findViewById(R.id.subcategorydescription)
            row=itemView.findViewById(R.id.subCategoryCardView)
            img=itemView.subcategoryimage
            pb=itemView.subcategory_progressbar
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.subcategory_card_view, parent, false)
        return MyViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = subcategoylist?.get(position)!!.name
        holder.description.text = "$ "+subcategoylist?.get(position)!!.description
        Glide.with(holder.img.context).load(subcategoylist.get(position).image).into(holder.img)
        val x1=subcategoylist.get(position).description
        val i1 = x1.split(",")[0]
        val i2=x1.split(",")[1]
        var nr=i1+"."+i2
        var x12 = subcategoylist.get(0).description
        val i12=x12.split(",")[0]
        val i22=x12.split(",")[1]
        var nr2=i12+"."+i22
        holder.pb.progress=((nr.toFloat().times(100)).div(nr2.toFloat())).toInt()




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