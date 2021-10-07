package com.example.myapplication.ui.fragments.tabFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.R


class OverviewFragment : Fragment() {

    private lateinit var viewOfLayout: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        viewOfLayout = inflater.inflate(R.layout.fragment_overview, container, false)
        val button1= viewOfLayout.findViewById<Button>(R.id.buyButton)
        button1.setOnClickListener {
            button1.startAnimation(AnimationUtils.loadAnimation(this.activity,R.anim.bounce))
        }
        val button2= viewOfLayout.findViewById<Button>(R.id.manageButton)
        button2.setOnClickListener {
            button2.startAnimation(AnimationUtils.loadAnimation(this.activity,R.anim.bounce))
        }
        val button3= viewOfLayout.findViewById<Button>(R.id.viewButton)
        button3.setOnClickListener {
            button3.startAnimation(AnimationUtils.loadAnimation(this.activity,R.anim.bounce))
        }



        return viewOfLayout
    }

}