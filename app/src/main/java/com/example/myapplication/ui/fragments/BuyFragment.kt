package com.example.myapplication.ui.fragments

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_buy.view.*
import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapplication.data.data.model.InvestedCoin
import com.example.myapplication.data.data.model.Transaction
import com.example.myapplication.repository.database.InvestedCoinDatabase
import com.example.myapplication.repository.database.TransactionDatabase
import kotlinx.android.synthetic.main.fragment_buy.view.exchange_rate
import kotlinx.android.synthetic.main.fragment_buy.view.fromValue
import kotlinx.android.synthetic.main.fragment_buy.view.toValue
import kotlinx.android.synthetic.main.fragment_sell.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class BuyFragment : Fragment() {
    private lateinit var viewOfLayout: View


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_buy, container, false)

        val id = arguments?.getString("id")
        val current_price = arguments?.getDouble("current_price")
        val symbol = arguments?.getString("symbol")
        val image = arguments?.getString("image")
        val name = arguments?.getString("name")
        val marketcap_rank = arguments?.getString("marketcap_rank")
        if (symbol != null) {
            viewOfLayout.toName.text = symbol.toUpperCase()
            viewOfLayout.exchange_rate.text =
                "1 " + symbol.toUpperCase() + " = " + current_price.toString() + " USDT"
        }


        val dbInvested = InvestedCoinDatabase.getDatabase(this.requireContext())
        val dbTransaction = TransactionDatabase.getDatabase(this.requireContext())
        for (t: Transaction in dbTransaction.TransactionDao().loadAllNoSync()) {
            Log.v("AdiTag", t.toString())
        }
        for (i: InvestedCoin in dbInvested.investedCoinDao().loadAllNoSync()) {
            Log.v("AdiTag", i.toString())
        }

        setUpWallet(dbInvested)

        val fromEditText = viewOfLayout.fromValue
        val toEditText = viewOfLayout.toValue
        viewOfLayout.buyButton.setOnClickListener {
            viewOfLayout.buyButton.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.bounce
                )
            )
            viewOfLayout.buyButton.isEnabled = false
            Handler().postDelayed({
                viewOfLayout.buyButton.setEnabled(true);
                Log.d("AdiTag", "you can buy again");
            }, 4000)
            val dbInvested = InvestedCoinDatabase.getDatabase(this.requireContext())
            if (fromEditText.text.toString().isNotEmpty()) {
                val now = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss.SSS")
                val formatted = now.format(formatter)
                val dbTransaction = TransactionDatabase.getDatabase(this.requireContext())
                var balance=0.0
                if (dbTransaction.TransactionDao().load() != null && dbInvested.investedCoinDao().load()!=null) {
                    for (invested: InvestedCoin in dbInvested.investedCoinDao().loadAllNoSync()) {
                        if (invested.id_user == "0" && invested.symbol == "USDT") {
                            balance=invested.invested_amount
                        }
                    }
                    if(fromEditText.text.toString().toDouble()<=balance) {
                        val transaction = Transaction(
                            dbTransaction.TransactionDao().load().id + 1,
                            "0",
                            id!!,
                            formatted,
                            "USDC",
                            symbol!!,
                            fromEditText.text.toString().toDouble(),
                            toEditText.text.toString().toDouble()
                        )
                        dbTransaction.TransactionDao().insert(transaction)
                        var ok = 1
                        for (invested: InvestedCoin in dbInvested.investedCoinDao().loadAllNoSync()) {
                            if (invested.id_user == "0" && invested.symbol == "USDT") {
                                val investedToUpdate = InvestedCoin(
                                    invested.symbol, invested.name, invested.symbol,
                                    invested.image, invested.rank, invested.id_user,
                                    invested.invested_amount - fromEditText.text.toString().toDouble()
                                )
                                dbInvested.investedCoinDao().insert(investedToUpdate)
                            }
                            if (invested.id_user == "0" && invested.symbol == symbol) {
                                ok = 0
                                val investedToUpdate = InvestedCoin(
                                    invested.symbol, invested.name, invested.symbol,
                                    invested.image, invested.rank, invested.id_user,
                                    invested.invested_amount + toEditText.text.toString().toDouble()
                                )
                                dbInvested.investedCoinDao().insert(investedToUpdate)
                            }
                        }
                        if (ok == 1) {
                            val investedToAdd = InvestedCoin(
                                symbol,
                                name!!,
                                symbol,
                                image!!,
                                marketcap_rank!!,
                                "0",
                                toEditText.text.toString().toDouble()
                            )

                            dbInvested.investedCoinDao().insert(investedToAdd)
                        }
                    }
                    else{
                        Toast.makeText(activity,"Insufficient balance",Toast.LENGTH_LONG).show()
                    }
                } else {
                    val dbTransaction = TransactionDatabase.getDatabase(this.requireContext())
                    var balance=0.0
                    for (invested: InvestedCoin in dbInvested.investedCoinDao().loadAllNoSync()) {
                        if (invested.id_user == "0" && invested.symbol == "USDT") {
                            balance=invested.invested_amount
                        }
                    }
                    if(fromEditText.text.toString().toDouble()<=balance) {
                        val transaction = Transaction(
                            0,
                            "0",
                            id!!,
                            formatted,
                            "USDT",
                            symbol!!,
                            fromEditText.text.toString().toDouble(),
                            toEditText.text.toString().toDouble()
                        )
                        dbTransaction.TransactionDao().insert(transaction)
                        var ok = 1
                        for (invested: InvestedCoin in dbInvested.investedCoinDao().loadAllNoSync()) {
                            if (invested.id_user == "0" && invested.symbol == "USDT") {
                                val investedToUpdate = InvestedCoin(
                                    invested.symbol, invested.name, invested.symbol,
                                    invested.image, invested.rank, invested.id_user,
                                    invested.invested_amount - fromEditText.text.toString().toDouble()
                                )
                                dbInvested.investedCoinDao().insert(investedToUpdate)
                            }
                            if (invested.id_user == "0" && invested.symbol == symbol) {
                                ok = 0
                                val investedToUpdate = InvestedCoin(
                                    invested.symbol, invested.name, invested.symbol,
                                    invested.image, invested.rank, invested.id_user,
                                    invested.invested_amount + toEditText.text.toString().toDouble()
                                )
                                dbInvested.investedCoinDao().insert(investedToUpdate)
                            }
                        }
                        if (ok == 1) {
                            val investedToAdd = InvestedCoin(
                                symbol,
                                name!!,
                                symbol,
                                image!!,
                                marketcap_rank!!,
                                "0",
                                toEditText.text.toString().toDouble()
                            )

                            dbInvested.investedCoinDao().insert(investedToAdd)
                        }
                    }
                }
            }
        setUpWallet(dbInvested)
        }

        fromEditText.addTextChangedListener(object : TextWatcher {
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
                if (s.toString() != "") {
                    val text = String.format("%.10f", s.toString().toDouble() / current_price!!)
                        .replace(",", ".")
                    viewOfLayout.toValue.setText(text)
                } else {
                    viewOfLayout.toValue.setText("")
                }
            }
        })

        setToolBarBackButton()

        return viewOfLayout
    }

    private fun setToolBarBackButton() {
        val toolbar = viewOfLayout.buyToolbar
        toolbar.navigationIcon?.setColorFilter(Color.parseColor("#e8a48f"), PorterDuff.Mode.SRC_IN)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()

        }

    }
    private fun setUpWallet(dbInvested: InvestedCoinDatabase) {
        var ok=1
        if (dbInvested.investedCoinDao().load() != null) {
            for (invested: InvestedCoin in dbInvested.investedCoinDao().loadAllNoSync()) {
                if (invested.id_user == "0" && invested.symbol.equals(
                        viewOfLayout.toName.text.toString(),
                        ignoreCase = true
                    )
                ) {
                    ok=0
                    viewOfLayout.symbolWallet.text =
                        invested.symbol + " : " + invested.invested_amount
                }
                if (invested.id_user == "0" && invested.symbol == "USDT") {
                    viewOfLayout.usdtWallet.text = "USDT : " + invested.invested_amount
                }
            }
            if (ok==1){
                viewOfLayout.symbolWallet.text =
                    viewOfLayout.toName.text.toString().toUpperCase() + " : 0"
            }
        }
    }

}