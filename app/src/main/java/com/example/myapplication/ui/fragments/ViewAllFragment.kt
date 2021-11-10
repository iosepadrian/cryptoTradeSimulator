package com.example.myapplication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.categoryAdapters.CategoryAdapter
import com.example.myapplication.data.data.adapters.categoryAdapters.SubCategoryAdapter
import com.example.myapplication.data.data.model.CoinApi
import com.example.myapplication.data.data.model.SubCategory
import kotlinx.android.synthetic.main.fragment_view_all.view.*


class ViewAllFragment : Fragment() ,SearchView.OnQueryTextListener{

    private lateinit var viewOfLayout: View
    private lateinit var adapter:SubCategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_view_all, container, false)
        // Inflate the layout for this fragment
        val list= arguments?.getSerializable("SubCategory") as ArrayList<SubCategory>
        Log.v("AdiTag",list.toString())
        val editsearch = viewOfLayout.findViewById<SearchView>(R.id.viewallsearch)
        editsearch.setOnQueryTextListener(this)

        val manager = LinearLayoutManager(context)
        adapter = SubCategoryAdapter()
        val recyclerView=viewOfLayout.subcategoryRecyclerView2
        recyclerView.layoutManager=manager
        recyclerView.adapter=adapter
        renderCoinsList(list)
        adapter.setOnItemClickListener(object : SubCategoryAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Clicked")
            }
        })

        return viewOfLayout
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (p0 != null) {
            adapter.filter.filter(p0.toLowerCase())
        }
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0 != null) {
            adapter.filter.filter(p0.toLowerCase())
        }
        return false
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun renderCoinsList(coinsList: List<SubCategory>) {
        adapter.addData(coinsList)
        adapter.notifyDataSetChanged()

    }

}