package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.api.ApiService
import com.example.myapplication.data.model.CoinApiAdapter
import com.example.myapplication.data.model.CoinDetailsAdapter
import com.example.myapplication.modelView.CoinDetailsViewModel
import com.example.myapplication.modelView.CoinViewModel
import com.example.myapplication.repository.CoinDetailsRepository
import com.example.myapplication.repository.CoinRepository
import com.example.myapplication.ui.CoinDetailsFactory
import com.example.myapplication.ui.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import android.R

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_deatil.view.*


class DeatilFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private lateinit var viewModel: CoinDetailsViewModel
    private lateinit var factory: CoinDetailsFactory
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(com.example.myapplication.R.layout.fragment_deatil, container, false)

        /*val api= ApiService()
        val repository= CoinDetailsRepository(api)
        GlobalScope.launch(Dispatchers.Main) {
            val coins = repository.getCoinDetails("01coin")
            Log.v("Aditag", coins.toString())
        }*/

        val id= arguments?.getString("id")
        val api=ApiService()
        val repository=CoinDetailsRepository(api)
        factory = CoinDetailsFactory(repository)
        viewModel= ViewModelProviders.of(this,factory).get(CoinDetailsViewModel::class.java)
        if (id != null) {
            viewModel.getcoinDetails(id)
            viewModel.getcoinTopDetails(id)
        }
        if (id != null) {
            Log.v("AdiTag",id)
        }

        viewModel.topDetails.observe(viewLifecycleOwner, Observer { topDetails->
            viewOfLayout.coinRank.text="#"+topDetails.coingecko_rank.toString()
            viewOfLayout.headerId.text=topDetails.name
            Glide.with(viewOfLayout.coinimage.context).load(topDetails.image.small).into(viewOfLayout.coinimage)
        })
        viewOfLayout.findViewById<TextView>(com.example.myapplication.R.id.headerId).text=id

        val recyclerView =  viewOfLayout.findViewById<RecyclerView>(com.example.myapplication.R.id.detailRecyclerView)
        viewModel.details.observe(viewLifecycleOwner, Observer { details ->
            recyclerView.also {
                var adapter= CoinDetailsAdapter(details)
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
            }
        })

        swipeRefreshLayout=viewOfLayout.swiperefresh
        swipeRefreshLayout.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                viewModel.details.observe(viewLifecycleOwner, Observer { details ->
                    recyclerView.also {
                        var adapter= CoinDetailsAdapter(details)
                        it.layoutManager = LinearLayoutManager(requireContext())
                        it.setHasFixedSize(true)
                        it.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                })
            swipeRefreshLayout.isRefreshing=false
            }

        })

        return viewOfLayout
    }


}