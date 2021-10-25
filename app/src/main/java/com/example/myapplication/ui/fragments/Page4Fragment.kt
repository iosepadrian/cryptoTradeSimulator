package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.categoryAdapters.CategoryAdapter
import com.example.myapplication.data.data.model.Category
import kotlinx.android.synthetic.main.fragment_page4.view.*
import com.example.myapplication.data.data.model.SubCategory





class Page4Fragment : Fragment() {

    private lateinit var viewOfLayout: View
    private var categoryList= ArrayList<Category>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_page4, container, false)

        val recyclerView=viewOfLayout.categoryRecyclerView
        createDummyData()

        val manager=LinearLayoutManager(requireContext())
        val adapter=CategoryAdapter(requireContext(),categoryList,-1,-1)
        recyclerView.layoutManager=manager
        recyclerView.adapter=adapter
        return viewOfLayout
    }

    private fun createDummyData() {
        var ex1= ArrayList<SubCategory>()
        var ex2= ArrayList<SubCategory>()
        var ex3= ArrayList<SubCategory>()
        var ex4= ArrayList<SubCategory>()
        var ex5= ArrayList<SubCategory>()
        categoryList.add(Category(1, "one",ex1 ))
        categoryList[0]?.subcategoryList.add(SubCategory("1", "sub one"))
        categoryList[0]?.subcategoryList.add(SubCategory("2", "sub two"))
        categoryList[0]?.subcategoryList.add(SubCategory("3", "sub three"))

        categoryList.add(Category(2,"two",ex2))
        categoryList[1]?.subcategoryList.add(SubCategory("1", "sub one"))
        categoryList[1]?.subcategoryList.add(SubCategory("2", "sub two"))
        categoryList[1]?.subcategoryList.add(SubCategory("3", "sub three"))

        categoryList.add(Category(3, "three",ex3))
        categoryList[2]?.subcategoryList.add(SubCategory("1", "sub one"))
        categoryList[2]?.subcategoryList.add(SubCategory("2", "sub two"))
        categoryList[2]?.subcategoryList.add(SubCategory("3", "sub three"))

        categoryList.add(Category(4, "four",ex4))
        categoryList[3]?.subcategoryList.add(SubCategory("1", "sub one"))
        categoryList[3]?.subcategoryList.add(SubCategory("2", "sub two"))
        categoryList[3]?.subcategoryList.add(SubCategory("3", "sub three"))

        categoryList.add(Category(5, "five",ex5))
        categoryList[4]?.subcategoryList.add(SubCategory("1", "sub one"))
        categoryList[4]?.subcategoryList.add(SubCategory("2", "sub two"))
        categoryList[4]?.subcategoryList.add(SubCategory("3", "sub three"))

        Log.v("AdiTag","aici "+ categoryList[3].subcategoryList.toString())
    }


}