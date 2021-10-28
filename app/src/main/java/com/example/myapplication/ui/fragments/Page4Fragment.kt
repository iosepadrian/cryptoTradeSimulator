package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadhamwi.tabsync.TabbedListMediator
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.categoryAdapters.CategoryAdapter
import com.example.myapplication.data.data.model.Category
import kotlinx.android.synthetic.main.fragment_page4.view.*
import com.example.myapplication.data.data.model.SubCategory
import com.google.android.material.tabs.TabLayout


class Page4Fragment : Fragment() {

    private lateinit var viewOfLayout: View
    private var categoryList= ArrayList<Category>()
    private var selectedSubCatPosition: Int = 0
    private lateinit var tabLayout:TabLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_page4, container, false)

        recyclerView=viewOfLayout.categoryRecyclerView
        createDummyData()

        tabLayout=viewOfLayout.tabLayout

        initRecycler()
        initTabLayout()
        initMediator()

        return viewOfLayout
    }

    private fun initRecycler() {
        val manager=LinearLayoutManager(requireContext())
        val adapter=CategoryAdapter(requireContext(),categoryList,-1,-1)
        recyclerView.layoutManager=manager
        recyclerView.adapter=adapter

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
        categoryList.add(Category(1, "Sandwich",ex1 ))
        categoryList[0]?.subcategoryList.add(SubCategory("Hot Dog", "Hot dog cu cârnați ucraineană"))
        categoryList[0]?.subcategoryList.add(SubCategory("Kaskrut", "Umplutura se adaugă Harissa (pasta acută de ardei iute), sărat coji de"))
        categoryList[0]?.subcategoryList.add(SubCategory("Panini", "Sandviș italian, care este strâns presată pe capacul grătar"))

        categoryList.add(Category(2,"Pizza",ex2))
        categoryList[1]?.subcategoryList.add(SubCategory("Prosciutto e funghi", "Cu șuncă fiartă (la cuptor) sau jambon crud"))
        categoryList[1]?.subcategoryList.add(SubCategory("Quattro Stagioni", "Suncă, ciuperci, anghinare și măsline negre"))
        categoryList[1]?.subcategoryList.add(SubCategory("Capricciosa", "Ingredientele sunt amestecate pe patul de sos de roșii și mozzarella"))

        categoryList.add(Category(3, "Supa",ex3))
        categoryList[2]?.subcategoryList.add(SubCategory("Ciorba de burta", "Burta taiata suvite, la sfarsit se pune sare, otet si usturoi"))
        categoryList[2]?.subcategoryList.add(SubCategory("Ciorba de porc", "1 ceapa,2 rosii,2 morcovi,1 telina,1 pastarnac,leustean verde,bors dupa gust" ))
        categoryList[2]?.subcategoryList.add(SubCategory("Ciorba de perisoare", "Zarzavatul se taie cubulete sau se da pe razatoare, ceapa se toaca marunt, apoi se pun la fiert intr-o oala"))

        categoryList.add(Category(4, "Paste",ex4))
        categoryList[3]?.subcategoryList.add(SubCategory("Spaghete cu ton si usturoi", "300 g spaghete/bucatini,4 catei usturoi,50 ml ulei de masline,1-2 ardei iute uscat"))
        categoryList[3]?.subcategoryList.add(SubCategory("Gnocchi de ricotta cu ton", "1 ou mediu,50 g parmezan ras fin,1/2 lingurita mica rasa sare,250 g ricotta bine scursa/urda"))
        categoryList[3]?.subcategoryList.add(SubCategory("Paste cu ton si lamaie", "3 linguri ulei de masline,1 lingura rasa patrunjel proaspat tocat,1 conserva ton,200 g paste format lung"))

        categoryList.add(Category(5, "Crispy",ex5))
        categoryList[4]?.subcategoryList.add(SubCategory("Crispy strips", "500 g piept de pui fara os,3 oua,fulgi de porumb"))
        categoryList[4]?.subcategoryList.add(SubCategory("Hot wings", "marinada: boia iute, sos chilli, usturoi taiat in bucatele, sos de soia, ulei de masline, si sare"))
        categoryList[4]?.subcategoryList.add(SubCategory("CASCAVAL PANE", "400 g cascaval trapist,2 oua,faina,pesmet"))

    }


}