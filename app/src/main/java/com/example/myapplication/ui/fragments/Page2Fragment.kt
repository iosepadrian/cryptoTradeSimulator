package com.example.myapplication.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.CoinApiAdapter
import com.example.myapplication.data.data.api.ApiService
import com.example.myapplication.data.data.model.CoinApi
import com.example.myapplication.modelView.CoinViewModel
import com.example.myapplication.modelView.factories.ViewModelFactory
import com.example.myapplication.repository.CoinRepository
import com.example.myapplication.repository.FavCoinRepository
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.buttom_sheet_dialog.*
import kotlinx.android.synthetic.main.fragment_deatil.view.*
import kotlinx.android.synthetic.main.fragment_page2.view.*

class Page2Fragment : Fragment(),SearchView.OnQueryTextListener {

    private lateinit var viewOfLayout: View
    private lateinit var viewModel: CoinViewModel
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: CoinApiAdapter
    var navc:NavController ?=null
    private var checkString="asc"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc=Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        viewOfLayout = inflater.inflate(R.layout.fragment_page2, container, false)

       // val recyclerView =  viewOfLayout.findViewById<RecyclerView>(R.id.secondPageRecyclerView)

        /*val repository=CoinRepository(ApiService())
        GlobalScope.launch(Dispatchers.Main) {
            val coins=repository.getCoins()
            Toast.makeText(activity,coins.toString(),Toast.LENGTH_LONG).show()
            Log.v("Aditag",coins.toString())
        }*/


        val api=ApiService()
        val repository=CoinRepository(api)
        factory = ViewModelFactory(repository)
        viewModel= ViewModelProviders.of(this,factory).get(CoinViewModel::class.java)
        viewModel.getCoins()
        val recyclerView =  viewOfLayout.secondPageRecyclerView

        val editsearch = viewOfLayout.findViewById<SearchView>(R.id.search)
        editsearch.setOnQueryTextListener(this)
        /*val icon: ImageView =
            editsearch.findViewById(R.id.search_button)
        icon.setColorFilter(Color.BLACK)*/

        adapter= CoinApiAdapter()
        viewModel.coins.observe(viewLifecycleOwner, { coins ->
            recyclerView.also {

                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
                renderPhotosList(coins)

            }
            viewOfLayout.noofcoins.text=coins.size.toString()+" coins"
        })


        val ascdscimage=viewOfLayout.ascdscimg
        if(checkString=="asc"){
            ascdscimage.setImageResource(R.drawable.asc)
        }
        else {
            ascdscimage.setImageResource(R.drawable.dsc)
            sortbyDescendingRank(recyclerView)
        }
        ascdscimage.setOnClickListener{
            if(checkString == "asc") {
                ascdscimage.setImageResource(R.drawable.dsc)
                checkString="dsc"
                if(viewOfLayout.sorttextview.text=="Sort: Name") {
                    sortbyDescendingName(recyclerView)
                }
                else{
                    if (viewOfLayout.sorttextview.text=="Sort: Price")
                    {
                        sortbyDescendingPrice(recyclerView)
                    }
                    else{
                        sortbyDescendingRank(recyclerView)
                    }
                }
            }
            else
            {
                ascdscimage.setImageResource(R.drawable.asc)
                checkString="asc"
                if(viewOfLayout.sorttextview.text=="Sort: Name") {
                    sortCoinsByName(recyclerView)
                }
                else{
                    if (viewOfLayout.sorttextview.text=="Sort: Price")
                    {
                        sortCoinsByPrice(recyclerView)
                    }
                    else{
                        sortCoinsByRank(recyclerView)
                    }
                }
            }
        }

        val sort=viewOfLayout.sorttextview
        sort.setOnClickListener{
            showBottomSheetDialog(recyclerView)
        }









        adapter.setOnItemClickListener(object : CoinApiAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page2Fragment_to_deatilFragment,bundle)
            }
        })




        return viewOfLayout
    }

    private fun sortbyDescendingName(recyclerView: RecyclerView) {

            adapter= CoinApiAdapter()
            viewModel.coins.observe(viewLifecycleOwner, { coins ->
                recyclerView.also {

                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                    renderPhotosList(coins.sortedByDescending { it.name })

                }
                viewOfLayout.noofcoins.text=coins.size.toString()+" coins"
            })
            adapter.setOnItemClickListener(object : CoinApiAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    Log.v("AdiTag","Item clicked")
                    val bundle = Bundle()
                    bundle.putString("id", adapter.returnData()[position].id)
                    navc?.navigate(R.id.action_page2Fragment_to_deatilFragment,bundle)
                }
            })

    }
    private fun sortbyDescendingPrice(recyclerView: RecyclerView) {

            adapter= CoinApiAdapter()
            viewModel.coins.observe(viewLifecycleOwner, { coins ->
                recyclerView.also {

                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                    renderPhotosList(coins.sortedByDescending { it.current_price})

                }
                viewOfLayout.noofcoins.text=coins.size.toString()+" coins"
            })
            adapter.setOnItemClickListener(object : CoinApiAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    Log.v("AdiTag","Item clicked")
                    val bundle = Bundle()
                    bundle.putString("id", adapter.returnData()[position].id)
                    navc?.navigate(R.id.action_page2Fragment_to_deatilFragment,bundle)
                }
            })

    }
    private fun sortbyDescendingRank(recyclerView: RecyclerView) {

        adapter= CoinApiAdapter()
        viewModel.coins.observe(viewLifecycleOwner, { coins ->
            recyclerView.also {

                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
                renderPhotosList(coins.sortedByDescending { it.market_cap_rank})

            }
            viewOfLayout.noofcoins.text=coins.size.toString()+" coins"
        })
        adapter.setOnItemClickListener(object : CoinApiAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page2Fragment_to_deatilFragment,bundle)
            }
        })

    }

    private fun showBottomSheetDialog(recyclerView: RecyclerView) {

        val buttomsheet=BottomSheetDialog(requireContext())
        buttomsheet.setContentView(R.layout.buttom_sheet_dialog)
        val name=buttomsheet.nameLinearLayout
        val price=buttomsheet.priceLinearLayout
        val rank=buttomsheet.rankLinearLaySout
        buttomsheet.show()

        name.setOnClickListener{
            viewOfLayout.sorttextview.text="Sort: Name"
            if(checkString=="asc")
                sortCoinsByName(recyclerView)
            else
                sortbyDescendingName(recyclerView)
            buttomsheet.dismiss()
        }
        price.setOnClickListener {
            viewOfLayout.sorttextview.text="Sort: Price"
            if(checkString=="asc")
                sortCoinsByPrice(recyclerView)
            else
                sortbyDescendingPrice(recyclerView)
            buttomsheet.dismiss()
        }
        rank.setOnClickListener {
            viewOfLayout.sorttextview.text="Sort: Rank"
            if(checkString=="asc")
                sortCoinsByRank(recyclerView)
            else
                sortbyDescendingRank(recyclerView)
            buttomsheet.dismiss()
        }
    }

    private fun sortCoinsByName(recyclerView:RecyclerView) {

        adapter= CoinApiAdapter()
        viewModel.coins.observe(viewLifecycleOwner, { coins ->
            recyclerView.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
                renderPhotosList(coins.sortedBy { it.name })

            }
            viewOfLayout.noofcoins.text=coins.size.toString()+" coins"
        })
        adapter.setOnItemClickListener(object : CoinApiAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page2Fragment_to_deatilFragment,bundle)
            }
        })
    }
    private fun sortCoinsByPrice(recyclerView:RecyclerView) {

        adapter= CoinApiAdapter()
        viewModel.coins.observe(viewLifecycleOwner, { coins ->
            recyclerView.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
                renderPhotosList(coins.sortedBy { it.current_price })

            }
            viewOfLayout.noofcoins.text=coins.size.toString()+" coins"
        })
        adapter.setOnItemClickListener(object : CoinApiAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page2Fragment_to_deatilFragment,bundle)
            }
        })
    }
    private fun sortCoinsByRank(recyclerView:RecyclerView) {

        adapter= CoinApiAdapter()
        viewModel.coins.observe(viewLifecycleOwner, { coins ->
            recyclerView.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
                renderPhotosList(coins)

            }
            viewOfLayout.noofcoins.text=coins.size.toString()+" coins"
        })
        adapter.setOnItemClickListener(object : CoinApiAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page2Fragment_to_deatilFragment,bundle)
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderPhotosList(photosList: List<CoinApi>) {
        adapter.addData(photosList)
        adapter.notifyDataSetChanged()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }




}