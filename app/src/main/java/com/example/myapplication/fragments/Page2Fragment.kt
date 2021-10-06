package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.api.ApiService
import com.example.myapplication.data.model.CoinApiAdapter
import com.example.myapplication.modelView.CoinViewModel
import com.example.myapplication.modelView.UserViewModel
import com.example.myapplication.repository.CoinRepository
import com.example.myapplication.ui.ViewModelFactory
import com.example.myapplication.utils.CoinApi
import kotlinx.android.synthetic.main.fragment_page2.*
import java.text.SimpleDateFormat
import java.util.*

class Page2Fragment : Fragment(),SearchView.OnQueryTextListener {

    private lateinit var viewOfLayout: View
    private lateinit var viewModel: CoinViewModel
    private lateinit var factory:ViewModelFactory
    private lateinit var adapter: CoinApiAdapter
    var navc:NavController ?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc=Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewOfLayout = inflater!!.inflate(R.layout.fragment_page2, container, false)

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
        val recyclerView =  viewOfLayout.findViewById<RecyclerView>(R.id.secondPageRecyclerView)

        val editsearch = viewOfLayout.findViewById<SearchView>(R.id.search)
        editsearch.setOnQueryTextListener(this)

        adapter=CoinApiAdapter()
        viewModel.coins.observe(viewLifecycleOwner, Observer { coins ->
            recyclerView.also {

                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
                renderPhotosList(coins)

            }
        })

        adapter.setOnItemClickListener(object : CoinApiAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page2Fragment_to_deatilFragment,bundle)
            }
        })

      /*  for(user:CoinApi in viewModel.coins.value!!)
        {
            Log.v("AdiTag",user.id)
        }*/


      /*  swipeToRefresh.setOnRefreshListener {
            Toast.makeText(activity,"Page refreshed", Toast.LENGTH_SHORT).show()
            swipeToRefresh.isRefreshing=false
        }
*/


        return viewOfLayout
    }

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