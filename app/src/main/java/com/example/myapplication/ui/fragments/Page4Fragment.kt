package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadhamwi.tabsync.TabbedListMediator
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.categoryAdapters.CategoryAdapter
import com.example.myapplication.data.data.adapters.categoryAdapters.SubCategoryAdapter
import com.example.myapplication.data.data.api.ApiService
import com.example.myapplication.data.data.model.Category
import kotlinx.android.synthetic.main.fragment_page4.view.*
import com.example.myapplication.data.data.model.SubCategory
import com.example.myapplication.modelView.CoinViewModel
import com.example.myapplication.modelView.factories.ViewModelFactory
import com.example.myapplication.repository.CoinRepository
import com.google.android.material.tabs.TabLayout


class Page4Fragment : Fragment() {

    private lateinit var viewOfLayout: View
    private var categoryList= ArrayList<Category>()
    private var selectedSubCatPosition: Int = 0
    private lateinit var tabLayout:TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CoinViewModel
    private lateinit var factory: ViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_page4, container, false)

        recyclerView=viewOfLayout.categoryRecyclerView


        tabLayout=viewOfLayout.tabLayout




        val api= ApiService()
        val repository= CoinRepository(api)
        factory = ViewModelFactory(repository)
        viewModel= ViewModelProviders.of(this,factory).get(CoinViewModel::class.java)
        viewModel.getCoins()

        createDummyData()


        return viewOfLayout
    }

    private fun initRecycler() {
        val manager=LinearLayoutManager(requireContext())
        val adapter=CategoryAdapter(requireContext(),categoryList,-1,-1)
        recyclerView.layoutManager=manager
        recyclerView.adapter=adapter
        adapter.setOnItemClickListener(object : CategoryAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
            }
        })

    }

    private fun initMediator() {
        TabbedListMediator(
            recyclerView,
            tabLayout,
            categoryList.indices.toList()
        ,true
        ).attach()
    }

    private fun initTabLayout() {
        for(category in categoryList){
            tabLayout.addTab(tabLayout.newTab().setText(category.name))
        }
    }


    private fun createDummyData() {
        var ex1= ArrayList<SubCategory>()
        var ex2= ArrayList<SubCategory>()
        var ex3= ArrayList<SubCategory>()
        var ex4= ArrayList<SubCategory>()
        var ex5= ArrayList<SubCategory>()


        viewModel.coins.observe(viewLifecycleOwner, { coins ->
                for (coin in coins)
                {
                    if (coin.current_price<1) {
                        ex1.add(
                            SubCategory(
                                coin.name,
                                String.format("%.10f",coin.current_price),
                                coin.current_price,
                                coin.image
                            )
                        )
                    }
                    else {
                        if (coin.current_price < 10){
                            ex2.add(
                                SubCategory(
                                    coin.name,
                                    String.format("%.10f",coin.current_price),
                                    coin.current_price,
                                    coin.image
                                )
                            )
                        }
                        else{
                            if (coin.current_price < 100){
                                ex3.add(
                                    SubCategory(
                                        coin.name,
                                        String.format("%.10f",coin.current_price),
                                        coin.current_price,
                                        coin.image
                                    )
                                )
                            }
                            else
                            {
                                if (coin.current_price < 1000){
                                    ex4.add(
                                        SubCategory(
                                            coin.name,
                                            String.format("%.10f",coin.current_price),
                                            coin.current_price,
                                            coin.image
                                        )
                                    )
                                }
                                else{
                                    if (coin.current_price < 100000){
                                        ex5.add(
                                            SubCategory(
                                                coin.name,
                                                String.format("%.10f",coin.current_price),
                                                coin.current_price,
                                                coin.image
                                            )
                                        )
                                    }
                                }

                            }
                        }

                    }

                }

            categoryList.add(Category(1, "0$-1$",ex1.sortedByDescending { it.price }))
            categoryList.add(Category(2,"1$-10$",ex2.sortedByDescending { it.price}))
            categoryList.add(Category(3, "10$-100$",ex3.sortedByDescending { it.price }))
            categoryList.add(Category(4, "100$-1000$",ex4.sortedByDescending { it.price }))
            categoryList.add(Category(5, "1000$-100000$",ex5.sortedByDescending { it.price }))

            initRecycler()
            initTabLayout()
            initMediator()
        })


    }


}