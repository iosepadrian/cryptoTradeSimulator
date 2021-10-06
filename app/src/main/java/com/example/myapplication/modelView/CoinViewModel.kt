package com.example.myapplication.modelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.api.Coroutines
import com.example.myapplication.repository.CoinRepository
import com.example.myapplication.utils.CoinApi
import kotlinx.coroutines.Job

class CoinViewModel(private val coinRepository: CoinRepository) : ViewModel() {

    private lateinit var job:Job
    private val _coins=MutableLiveData<List<CoinApi>>()
    val coins:LiveData<List<CoinApi>>
        get()=_coins



    fun getCoins(){
        job=Coroutines.ioThenMain(
            { coinRepository.getCoins()
            },
                {_coins.value=it }

        )

    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}


