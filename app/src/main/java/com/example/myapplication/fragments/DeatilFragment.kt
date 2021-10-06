package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.api.ApiService
import com.example.myapplication.data.model.CoinDetailsAdapter
import com.example.myapplication.modelView.CoinDetailsViewModel
import com.example.myapplication.repository.CoinDetailsRepository
import com.example.myapplication.ui.CoinDetailsFactory
import kotlinx.android.synthetic.main.fragment_deatil.view.*


class DeatilFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private lateinit var viewModel: CoinDetailsViewModel
    private lateinit var factory: CoinDetailsFactory



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(com.example.myapplication.R.layout.fragment_deatil, container, false)

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
            viewOfLayout.headerId.text=topDetails.name
            viewOfLayout.coinRank.text="#"+topDetails.coingecko_rank
            Glide.with(viewOfLayout.coinimage.context).load(topDetails.image.small).into(viewOfLayout.coinimage)
        })

        /*GlobalScope.launch(Dispatchers.Main) {
            val coins = repository.getCoinTopDetails("01coin")
            Log.v("Aditag", coins.toString())
        }*/


        val recyclerView =  viewOfLayout.detailRecyclerView
        viewModel.details.observe(viewLifecycleOwner, { details ->
            recyclerView.also {
                val adapter= CoinDetailsAdapter(details)
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
            }
        })

        return viewOfLayout
    }


}