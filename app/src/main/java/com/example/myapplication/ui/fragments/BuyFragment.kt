package com.example.myapplication.ui.fragments

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_buy.view.*
import android.text.Editable

import android.text.TextWatcher
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.fragment_buy.view.exchange_rate
import kotlinx.android.synthetic.main.fragment_buy.view.fromValue
import kotlinx.android.synthetic.main.fragment_buy.view.toName
import kotlinx.android.synthetic.main.fragment_buy.view.toValue
import kotlinx.android.synthetic.main.fragment_sell.view.*


class BuyFragment : Fragment() {
    private lateinit var viewOfLayout: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_buy, container, false)

        val id= arguments?.getString("id")
        val current_price= arguments?.getDouble("current_price")
        val symbol= arguments?.getString("symbol")
        if (symbol != null) {
            viewOfLayout.toName.text=symbol.toUpperCase()
            viewOfLayout.exchange_rate.text="1 "+symbol.toUpperCase()+" = "+current_price.toString()+" USDT"
        }
        viewOfLayout.buyButton.setOnClickListener {
            viewOfLayout.buyButton.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.bounce))
        }
        val et1= viewOfLayout.fromValue
        et1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {

            }

            override fun afterTextChanged(s: Editable) {
                if(s.toString()!="") {
                    val text = String.format("%.10f", s.toString().toDouble() / current_price!!)
                        .replace(",", ".")
                    viewOfLayout.toValue.setText(text)
                }
                else
                {
                    viewOfLayout.toValue.setText("")
                }
            }
        })

        setToolBarBackButton()

        return viewOfLayout
    }
    private fun setToolBarBackButton(){
        val toolbar=viewOfLayout.buyToolbar
        toolbar.    navigationIcon?.setColorFilter(Color.parseColor("#e8a48f"), PorterDuff.Mode.SRC_IN)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()

        }

    }


}