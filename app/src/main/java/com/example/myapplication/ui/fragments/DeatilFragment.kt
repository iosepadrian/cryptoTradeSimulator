package com.example.myapplication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.myapplication.data.data.adapters.CoinDetailsAdapter
import com.example.myapplication.data.data.api.ApiService
import com.example.myapplication.modelView.CoinDetailsViewModel
import com.example.myapplication.modelView.factories.CoinDetailsFactory
import com.example.myapplication.repository.CoinDetailsRepository
import kotlinx.android.synthetic.main.fragment_deatil.view.*


class DeatilFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private lateinit var viewModel: CoinDetailsViewModel
    private lateinit var factory: CoinDetailsFactory
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(com.example.myapplication.R.layout.fragment_deatil, container, false)

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

        viewModel.topDetails.observe(viewLifecycleOwner, { topDetails->
            viewOfLayout.coinRank.text="#"+ topDetails.coingecko_rank
            viewOfLayout.headerId.text=topDetails.name
            Glide.with(viewOfLayout.coinimage.context).load(topDetails.image.small).into(viewOfLayout.coinimage)
        })
        viewOfLayout.findViewById<TextView>(com.example.myapplication.R.id.headerId).text=id

        val recyclerView =  viewOfLayout.findViewById<RecyclerView>(com.example.myapplication.R.id.detailRecyclerView)
        viewModel.details.observe(viewLifecycleOwner, { details ->
            recyclerView.also {
                val adapter= CoinDetailsAdapter(details)
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
            }
        })

        swipeRefreshLayout=viewOfLayout.swiperefresh
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.details.observe(viewLifecycleOwner, { details ->
                recyclerView.also {
                    val adapter = CoinDetailsAdapter(details)
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            })
            swipeRefreshLayout.isRefreshing = false
        }

        return viewOfLayout
    }


}