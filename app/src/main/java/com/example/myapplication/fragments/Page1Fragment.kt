package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.fragments.tabFragments.LabelFragment
import com.example.myapplication.fragments.tabFragments.OverviewFragment
import com.example.myapplication.fragments.tabFragments.TasksFragment
import com.example.myapplication.fragments.tabFragments.TradesFragment
import com.example.myapplication.data.model.Coin
import com.example.myapplication.data.model.CoinListAdapter
import com.example.myapplication.data.model.PagerAdapter
import com.google.android.material.tabs.TabLayout

class Page1Fragment : Fragment() {

    private var coinlist = ArrayList<Coin>()
    private lateinit var viewOfLayout: View

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_page1, container, false)
        val tab = viewOfLayout.findViewById<TabLayout>(R.id.fragment1tabLayout)
        val viewPager = viewOfLayout.findViewById<ViewPager>(R.id.viewpager)
        val pagerAdapters = PagerAdapter(childFragmentManager)
        if (pagerAdapters != null) {
            pagerAdapters.addFragment(OverviewFragment(),"Overview")
            pagerAdapters.addFragment(TasksFragment(),"Tasks")
            pagerAdapters.addFragment(TradesFragment(),"Trades")
            pagerAdapters.addFragment(LabelFragment(),"Label")
        }else
        {
            print(Log.v("AdiTag","eroare1"))
        }
        viewPager.adapter=pagerAdapters
        tab.setupWithViewPager(viewPager)


        val coni1= Coin("Bitcoin","BTC","+ 40.22%")
        val coni2= Coin("Ethereum","ETH","+ 22.45%")
        val coni3= Coin("Solana","SOL","- 10.22%")

        coinlist.add(coni1)
        coinlist.add(coni2)
        coinlist.add(coni3)



        val recyclerView =  viewOfLayout.findViewById<RecyclerView>(R.id.coinRecyclerView)


        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            // set the custom adapter to the RecyclerView
            adapter =  CoinListAdapter(coinlist)

        }


        return viewOfLayout
    }

   /* val coin1=Coin("Bitcoin","BTC","+ 20.20%")
    val coin2=Coin("Ethereum","ETH","+ 30.22%")
    val coin3=Coin("Solana","SOL","- 01.33%")

    coinlist.toMutableList().add(coin1)
    coinlist.toMutableList().add(coin2)
    coinlist.toMutableList().add(coin3)*/



}