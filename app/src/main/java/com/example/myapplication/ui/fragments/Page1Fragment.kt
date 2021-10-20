package com.example.myapplication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.data.data.model.Coin
import com.example.myapplication.data.data.adapters.CoinListAdapter
import com.example.myapplication.data.data.adapters.CoinsListAdapter
import com.example.myapplication.data.data.adapters.PagerAdapter
import com.example.myapplication.ui.fragments.tabFragments.LabelFragment
import com.example.myapplication.ui.fragments.tabFragments.OverviewFragment
import com.example.myapplication.ui.fragments.tabFragments.TasksFragment
import com.example.myapplication.ui.fragments.tabFragments.TradesFragment
import com.google.android.material.tabs.TabLayout

class Page1Fragment : Fragment() {

    private var coinlist = ArrayList<Coin>()
    private lateinit var viewOfLayout: View
    private val adapter = CoinsListAdapter()
    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_page1, container, false)
        val tab = viewOfLayout.findViewById<TabLayout>(R.id.fragment1tabLayout)
        val viewPager = viewOfLayout.findViewById<ViewPager>(R.id.viewpager)
        val pagerAdapters = PagerAdapter(childFragmentManager)
        pagerAdapters.addFragment(OverviewFragment(),"Overview")
        pagerAdapters.addFragment(TasksFragment(),"Tasks")
        pagerAdapters.addFragment(TradesFragment(),"Trades")
        pagerAdapters.addFragment(LabelFragment(),"Label")
        viewPager.adapter=pagerAdapters
        tab.setupWithViewPager(viewPager)


        val coni1= Coin("Bitcoin","BTC","+ 40.22%")
        val coni2= Coin("Ethereum","ETH","+ 22.45%")
        val coni3= Coin("Solana","SOL","- 10.22%")

        coinlist.add(coni1)
        coinlist.add(coni2)
        coinlist.add(coni3)



        val recyclerView =  viewOfLayout.findViewById<RecyclerView>(R.id.coinRecyclerView)
        adapter.submitList(coinlist)

        recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter=adapter



        return viewOfLayout
    }

   /* val coin1=Coin("Bitcoin","BTC","+ 20.20%")
    val coin2=Coin("Ethereum","ETH","+ 30.22%")
    val coin3=Coin("Solana","SOL","- 01.33%")

    coinlist.toMutableList().add(coin1)
    coinlist.toMutableList().add(coin2)
    coinlist.toMutableList().add(coin3)*/



}