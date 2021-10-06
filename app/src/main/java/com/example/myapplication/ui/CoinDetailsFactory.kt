package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.modelView.CoinDetailsViewModel
import com.example.myapplication.modelView.CoinViewModel
import com.example.myapplication.repository.CoinDetailsRepository

class CoinDetailsFactory(private val repository: CoinDetailsRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinDetailsViewModel(repository) as T
    }

}