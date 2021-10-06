package com.example.myapplication.fragments.tabFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import com.example.myapplication.R


class LabelFragment : Fragment() {

    private lateinit var viewOfLayout: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewOfLayout = inflater.inflate(R.layout.fragment_label, container, false)
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