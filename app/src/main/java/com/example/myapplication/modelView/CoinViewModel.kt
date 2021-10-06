package com.example.myapplication.modelView

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.api.Coroutines
import com.example.myapplication.data.model.Coin
import com.example.myapplication.repository.CoinRepository
import com.example.myapplication.utils.CoinApi
import com.example.myapplication.utils.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
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


