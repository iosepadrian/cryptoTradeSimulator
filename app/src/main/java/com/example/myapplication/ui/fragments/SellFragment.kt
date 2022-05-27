package com.example.myapplication.ui.fragments

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapplication.R
import com.example.myapplication.data.data.model.InvestedCoin
import com.example.myapplication.data.data.model.Transaction
import com.example.myapplication.repository.database.InvestedCoinDatabase
import com.example.myapplication.repository.database.TransactionDatabase
import kotlinx.android.synthetic.main.fragment_buy.view.exchange_rate
import kotlinx.android.synthetic.main.fragment_buy.view.fromValue
import kotlinx.android.synthetic.main.fragment_buy.view.toValue
import kotlinx.android.synthetic.main.fragment_sell.view.*
import kotlinx.android.synthetic.main.fragment_sell.view.sellButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class SellFragment : Fragment() {
    private lateinit var viewOfLayout: View

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewOfLayout = inflater.inflate(R.layout.fragment_sell, container, false)
        val id = arguments?.getString("id")
        val current_price = arguments?.getDouble("current_price")
        val symbol = arguments?.getString("symbol")
        if (symbol != null) {
            viewOfLayout.fromNameSymbol.text = symbol.uppercase(Locale.getDefault())
            viewOfLayout.exchange_rate.text =
                "1 " + symbol.toUpperCase() + " = " + current_price.toString() + " USDT"
        }

        val fromEditText = viewOfLayout.fromValue
        val toEditText = viewOfLayout.toValue

        val dbInvested = InvestedCoinDatabase.getDatabase(this.requireContext())
        setUpWallet(dbInvested)

        viewOfLayout.sellButton.setOnClickListener {
            viewOfLayout.sellButton.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.bounce
                )
            )
            viewOfLayout.sellButton.isEnabled = false
            Handler().postDelayed({
                viewOfLayout.sellButton.setEnabled(true);
                Log.d("AdiTag", "you can sell again");
                Toast.makeText(requireContext(), "you can sell again", Toast.LENGTH_LONG).show()
            }, 4000)

            if (fromEditText.text.toString().isNotEmpty()) {
                val now = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss.SSS")
                val formatted = now.format(formatter)
                val dbTransaction = TransactionDatabase.getDatabase(this.requireContext())
                var balance = 0.0
                if (dbTransaction.TransactionDao().load() != null && dbInvested.investedCoinDao()
                        .load() != null
                ) {
                    for (invested: InvestedCoin in dbInvested.investedCoinDao().loadAllNoSync()) {
                        if (invested.id_user == "0" && invested.symbol.equals(
                                viewOfLayout.fromNameSymbol.text.toString(),
                                ignoreCase = true
                            )
                        ) {
                            balance = invested.invested_amount
                        }
                    }
                    if (fromEditText.text.toString().toDouble() <= balance) {
                        val transaction = Transaction(
                            dbTransaction.TransactionDao().load().id + 1,
                            "0",
                            id!!,
                            formatted,
                            symbol!!,
                            "USDC",
                            fromEditText.text.toString().toDouble(),
                            toEditText.text.toString().toDouble()
                        )
                        dbTransaction.TransactionDao().insert(transaction)
                        var ok = 1
                        for (invested: InvestedCoin in dbInvested.investedCoinDao()
                            .loadAllNoSync()) {
                            if (invested.id_user == "0" && invested.symbol == symbol) {
                                ok = 0
                                val investedToUpdate = InvestedCoin(
                                    invested.symbol, invested.name, invested.symbol,
                                    invested.image, invested.rank, invested.id_user,
                                    invested.invested_amount - fromEditText.text.toString()
                                        .toDouble()
                                )
                                dbInvested.investedCoinDao().insert(investedToUpdate)
                            }
                            if (invested.id_user == "0" && invested.symbol == "USDT") {
                                val investedToUpdate = InvestedCoin(
                                    invested.symbol, invested.name, invested.symbol,
                                    invested.image, invested.rank, invested.id_user,
                                    invested.invested_amount + toEditText.text.toString().toDouble()
                                )
                                dbInvested.investedCoinDao().insert(investedToUpdate)
                            }
                        }
                    } else {
                        Toast.makeText(activity, "Insufficient balance", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val dbTransaction = TransactionDatabase.getDatabase(this.requireContext())
                    var balance = 0.0
                    for (invested: InvestedCoin in dbInvested.investedCoinDao().loadAllNoSync()) {
                        if (invested.id_user == "0" && invested.symbol == symbol) {
                            balance = invested.invested_amount
                        }
                    }
                    if (fromEditText.text.toString().toDouble() <= balance) {
                        val transaction = Transaction(
                            0,
                            "0",
                            id!!,
                            formatted,
                            symbol!!,
                            "USDT",
                            fromEditText.text.toString().toDouble(),
                            toEditText.text.toString().toDouble()
                        )
                        dbTransaction.TransactionDao().insert(transaction)
                        var ok = 1
                        for (invested: InvestedCoin in dbInvested.investedCoinDao()
                            .loadAllNoSync()) {
                            if (invested.id_user == "0" && invested.symbol == symbol) {
                                val investedToUpdate = InvestedCoin(
                                    invested.symbol, invested.name, invested.symbol,
                                    invested.image, invested.rank, invested.id_user,
                                    invested.invested_amount - fromEditText.text.toString()
                                        .toDouble()
                                )
                                dbInvested.investedCoinDao().insert(investedToUpdate)
                            }
                            if (invested.id_user == "0" && invested.symbol == "USDC") {
                                ok = 0
                                val investedToUpdate = InvestedCoin(
                                    invested.symbol, invested.name, invested.symbol,
                                    invested.image, invested.rank, invested.id_user,
                                    invested.invested_amount + toEditText.text.toString().toDouble()
                                )
                                dbInvested.investedCoinDao().insert(investedToUpdate)
                            }
                        }
                    }
                }
            }
            setUpWallet(dbInvested)
        }

        val et1 = viewOfLayout.fromValue
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
                if (s.toString() != "") {
                    val text = String.format("%.10f", s.toString().toDouble() * current_price!!)
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

    private fun setUpWallet(dbInvested: InvestedCoinDatabase) {
        if (dbInvested.investedCoinDao().load() != null) {
            for (invested: InvestedCoin in dbInvested.investedCoinDao().loadAllNoSync()) {
                if (invested.id_user == "0" && invested.symbol.equals(
                        viewOfLayout.fromNameSymbol.text.toString(),
                        ignoreCase = true
                    )
                ) {
                    viewOfLayout.symbolWalletValue.text =
                        invested.symbol + " : " + invested.invested_amount
                }
                if (invested.id_user == "0" && invested.symbol == "USDT") {
                    viewOfLayout.usdtWalletValue.text = "USDT : " + invested.invested_amount
                }
            }
        }
    }

    private fun setToolBarBackButton() {
        val toolbar = viewOfLayout.sellToolbar
        toolbar.navigationIcon?.setColorFilter(Color.parseColor("#e8a48f"), PorterDuff.Mode.SRC_IN)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()

        }
    }
}