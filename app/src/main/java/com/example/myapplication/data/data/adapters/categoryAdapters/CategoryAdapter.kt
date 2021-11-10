package com.example.myapplication.data.data.adapters.categoryAdapters

import android.annotation.SuppressLint
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
import com.example.myapplication.data.data.model.CoinApi
import com.example.myapplication.data.data.model.SubCategory
import kotlinx.android.synthetic.main.categoty_card_view.view.*


class CategoryAdapter(
    private var context:Context,
    private var categoryList: ArrayList<Category>,
    private var clickedPosition:Int,
    private var subClickedPosition:Int

) : RecyclerView.Adapter<MyViewHolder>(){
    private lateinit var mListener: onItemClickListener
    private lateinit var adapter:SubCategoryAdapter
    private lateinit var adapter2:SubCategoryAdapter
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
    @SuppressLint("NotifyDataSetChanged")
    private fun renderCoinsList(coinsList: List<SubCategory>) {
        adapter.addData(coinsList)
        adapter.notifyDataSetChanged()

    }
    @SuppressLint("NotifyDataSetChanged")
    private fun renderCoinsList2(coinsList: List<SubCategory>) {
        adapter2.addData(coinsList)
        adapter2.notifyDataSetChanged()

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.setText(categoryList.get(position).name)
        val manager = LinearLayoutManager(context)
        adapter = SubCategoryAdapter()
        holder.mSubRecyclerView.layoutManager = manager
        holder.mSubRecyclerView.adapter = adapter
        renderCoinsList(categoryList[position].subcategoryList)
        adapter.setOnItemClickListener(object : SubCategoryAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
            }
        })
        val manager2 = LinearLayoutManager(context)
        adapter2 = SubCategoryAdapter()
        holder.mSubRecyclerView2.layoutManager = manager2
        holder.mSubRecyclerView2.adapter = adapter2
        renderCoinsList2(categoryList[position].subcategoryList.subList(0,5))
        adapter2.setOnItemClickListener(object : SubCategoryAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
            }
        })

        /*if (position == clickedPosition) {
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
        }*/
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
    fun returnData(): List<Category> {
        return categoryList
    }
}


