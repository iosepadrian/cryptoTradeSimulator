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
import android.util.Log

import android.util.TypedValue
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.LinearLayoutManager




class CategoryAdapter(
    private var context:Context,
    private var categoryList: ArrayList<Category>,
    private var clickedPosition:Int,
    private var subClickedPosition:Int
) : RecyclerView.Adapter<MyViewHolder>(){



    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var row: CardView
        val mSubRecyclerView:RecyclerView
        init {
            name = itemView.findViewById(R.id.categoryTextView)
            row=itemView.findViewById(R.id.categoryCardView)
            mSubRecyclerView=itemView.findViewById(R.id.subcategoryRecyclerView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.categoty_card_view, parent, false)
        return MyViewHolder(v)
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
        if (position == clickedPosition) {
            holder.mSubRecyclerView.visibility = View.VISIBLE
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            holder.name.setTypeface(null, Typeface.BOLD)
        } else {
            holder.mSubRecyclerView.visibility = View.GONE
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
            holder.name.setTypeface(null, Typeface.NORMAL)
        }

        holder.row.setOnClickListener {
            if (clickedPosition != position) {
                clickedPosition = position
                subClickedPosition = -1
                notifyDataSetChanged()
            } else {
                holder.mSubRecyclerView.visibility = View.GONE
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


