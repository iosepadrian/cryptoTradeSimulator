package com.example.myapplication.data.data.adapters.categoryAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.CoinApiAdapter
import com.example.myapplication.data.data.adapters.categoryAdapters.SubCategoryAdapter.*
import com.example.myapplication.data.data.model.SubCategory
import com.google.android.material.chip.Chip
import android.graphics.Typeface
import android.util.Log

import android.util.TypedValue
import android.widget.*
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.myapplication.data.data.model.CoinApi
import com.example.myapplication.data.data.model.Image
import kotlinx.android.synthetic.main.subcategory_card_view.view.*
import java.lang.String
import java.util.ArrayList


class SubCategoryAdapter(
): RecyclerView.Adapter<MyViewHolder>(),Filterable {

    private lateinit var mListener: onItemClickListener
    private lateinit var subcategoylist:List<SubCategory>
    private lateinit var subcategoylistFiltered:List<SubCategory>
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener=listener


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
        holder.name.text = subcategoylistFiltered?.get(position)!!.name
        holder.description.text = "$ "+subcategoylistFiltered?.get(position)!!.description
        Glide.with(holder.img.context).load(subcategoylistFiltered.get(position).image).into(holder.img)
        val x1=subcategoylistFiltered.get(position).description
   // Log.v("AdiTag",x1.toString())
    val i1 = x1.split(",",".")[0]
        val i2=x1.split(",",".")[1]
        var nr=i1+"."+i2
        var x12 = subcategoylistFiltered.get(0).description
        val i12=x12.split(",",".")[0]
        val i22=x12.split(",",".")[1]
        var nr2=i12+"."+i22
        holder.pb.progress=((nr.toFloat().times(100)).div(nr2.toFloat())).toInt()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<SubCategory>) {
        subcategoylist = list
        subcategoylistFiltered = subcategoylist
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return subcategoylistFiltered.size
    }
    fun returnData(): List<SubCategory> {
        return subcategoylistFiltered
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) subcategoylistFiltered = subcategoylist else {
                    val filteredList = ArrayList<SubCategory>()
                    subcategoylist
                        .filter {
                            (it.name.toLowerCase().contains(constraint!!)) or
                                    (it.name.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    subcategoylistFiltered = filteredList.toList()
                }
                return FilterResults().apply { values = subcategoylistFiltered }
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                subcategoylistFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as List<SubCategory>
                notifyDataSetChanged()
            }
        }
    }

}