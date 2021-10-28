package com.example.myapplication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.data.data.model.Coin
import com.example.myapplication.data.data.adapters.CoinListAdapter
import com.example.myapplication.data.data.adapters.CoinsListAdapter
import com.example.myapplication.data.data.adapters.FavCoinAdapter
import com.example.myapplication.data.data.adapters.PagerAdapter
import com.example.myapplication.modelView.FavCoinModelView
import com.example.myapplication.modelView.UserViewModel
import com.example.myapplication.ui.fragments.tabFragments.LabelFragment
import com.example.myapplication.ui.fragments.tabFragments.OverviewFragment
import com.example.myapplication.ui.fragments.tabFragments.TasksFragment
import com.example.myapplication.ui.fragments.tabFragments.TradesFragment
import com.google.android.material.tabs.TabLayout

class Page1Fragment : Fragment() {

    private lateinit var viewOfLayout: View
    private var adapter = CoinsListAdapter()
    private lateinit var viewModel: FavCoinModelView
    var navc: NavController?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc= Navigation.findNavController(view)
    }
    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        viewOfLayout = inflater.inflate(R.layout.fragment_page1, container, false)

        viewModel= ViewModelProvider(requireActivity()).get(FavCoinModelView::class.java)

        val tab = viewOfLayout.findViewById<TabLayout>(R.id.fragment1tabLayout)
        val viewPager = viewOfLayout.findViewById<ViewPager>(R.id.viewpager)
        val pagerAdapters = PagerAdapter(childFragmentManager)
        pagerAdapters.addFragment(OverviewFragment(),"Overview")
        pagerAdapters.addFragment(TasksFragment(),"Tasks")
        pagerAdapters.addFragment(TradesFragment(),"Trades")
        pagerAdapters.addFragment(LabelFragment(),"Label")
        viewPager.adapter=pagerAdapters
        tab.setupWithViewPager(viewPager)


        val recyclerView =  viewOfLayout.findViewById<RecyclerView>(R.id.coinRecyclerView)


        adapter= CoinsListAdapter()

        viewModel.favcoin.observe(viewLifecycleOwner, { details ->
            adapter.submitList(details)
            recyclerView.also {
                if (it != null) {
                    it.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                }
            }
        })
        adapter.setOnItemClickListener(object : CoinsListAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page1Fragment_to_deatilFragment,bundle)
            }
        })

        return viewOfLayout
    }



}