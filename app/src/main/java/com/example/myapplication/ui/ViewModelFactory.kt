package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.api.ApiHelper
import com.example.myapplication.modelView.CoinViewModel
import com.example.myapplication.repository.CoinRepository


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: CoinRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinViewModel(repository) as T
    }

}


