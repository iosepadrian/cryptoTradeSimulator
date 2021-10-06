package com.example.myapplication.modelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.api.Coroutines
import com.example.myapplication.repository.CoinDetailsRepository
import com.example.myapplication.utils.CoinApi
import com.example.myapplication.utils.CoinDetails
import kotlinx.coroutines.Job

class CoinDetailsViewModel(private val coinDetailsRepository: CoinDetailsRepository) : ViewModel() {

    private lateinit var job: Job
    private val _details= MutableLiveData<CoinDetails>()
    val details: LiveData<CoinDetails>
        get()=_details

    fun getcoinDetails(id:String){
        job= Coroutines.ioThenMain(
            { coinDetailsRepository.getCoinDetails(id)
            },
            {_details.value=it }

        )

    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }


}