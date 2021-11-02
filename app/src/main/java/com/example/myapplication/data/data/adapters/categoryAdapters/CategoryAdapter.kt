package com.example.myapplication.data.data.adapters.categoryAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.categoryAdapters.CategoryAdapter.MyViewHolder
import com.example.myapplication.data.data.model.Category
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log

import android.util.TypedValue
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.data.adapters.CoinApiAdapter
import kotlinx.android.synthetic.main.categoty_card_view.view.*


class CategoryAdapter(
    private var context:Context,
    private var categoryList: ArrayList<Category>,
    private var clickedPosition:Int,
    private var subClickedPosition:Int

) : RecyclerView.Adapter<MyViewHolder>(){
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener=listener


    }

    class MyViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var row: CardView
        var viewall: TextView
        val mSubRecyclerView:RecyclerView
        val mSubRecyclerView2:RecyclerView
        init {
            name = itemView.findViewById(R.id.categoryTextView)
            row=itemView.findViewById(R.id.categoryCardView)
            mSubRecyclerView=itemView.findViewById(R.id.subcategoryRecyclerView)
            mSubRecyclerView2=itemView.findViewById(R.id.subcategoryRecyclerView2)
            viewall=itemView.viewalltextview
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.categoty_card_view, parent, false)
        return MyViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.setText(categoryList.get(position).name)
        val manager = LinearLayoutManager(context)
        val adapter = SubCategoryAdapter(
            object : SubCategoryAdapter.SubSelectionInterface {
                override fun onsubselection(position: Int) {
                    subClickedPosition = position
                }
            },
            categoryList[position].subcategoryList,
            -1,
            )
        holder.mSubRecyclerView.layoutManager = manager
        holder.mSubRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : SubCategoryAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
            }
        })
        val manager2 = LinearLayoutManager(context)
        val adapter2 = SubCategoryAdapter(
            object : SubCategoryAdapter.SubSelectionInterface {
                override fun onsubselection(position: Int) {
                    subClickedPosition = position
                }
            },
            categoryList[position].subcategoryList.subList(0,5),
            -1,
        )
        holder.mSubRecyclerView2.layoutManager = manager2
        holder.mSubRecyclerView2.adapter = adapter2
        adapter2.setOnItemClickListener(object : SubCategoryAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
            }
        })

        if (position == clickedPosition) {
            holder.mSubRecyclerView.visibility = View.VISIBLE
            holder.mSubRecyclerView2.visibility = View.GONE
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
            holder.viewall.text="See less"
        } else {
            holder.mSubRecyclerView.visibility = View.GONE
            holder.mSubRecyclerView2.visibility = View.VISIBLE
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21F)
            holder.viewall.text="View all"
        }

        holder.row.setOnClickListener {
            if (clickedPosition != position) {
                clickedPosition = position
                subClickedPosition = -1
                notifyDataSetChanged()
            } else {
                holder.viewall.text="View all"
                holder.mSubRecyclerView.visibility = View.GONE
                holder.mSubRecyclerView2.visibility = View.VISIBLE
                clickedPosition = -1
                subClickedPosition = -1
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun getCategoryPosition(): Int {
        return clickedPosition
    }

    fun getSubCategoryPosition(): Int {
        return subClickedPosition
    }
}


