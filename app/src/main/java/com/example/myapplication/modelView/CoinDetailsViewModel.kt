package com.example.myapplication.modelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.data.api.Coroutines
import com.example.myapplication.repository.CoinDetailsRepository
import com.example.myapplication.data.data.model.CoinDetails
import com.example.myapplication.data.data.model.CoinTopDetails
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


    private val _one= MutableLiveData<CoinDetails>()
    val one: LiveData<CoinDetails>
        get()=_one

    fun getcoinDetailsForOneDay(id:String){
        job= Coroutines.ioThenMain(
            { coinDetailsRepository.getCoinDetailsForOneDay(id)
            },
            {_one.value=it }
        )

    }

    private val _two= MutableLiveData<CoinDetails>()
    val two: LiveData<CoinDetails>
        get()=_two

    fun getcoinDetailsForTwoDays(id:String){
        job= Coroutines.ioThenMain(
            { coinDetailsRepository.getCoinDetailsForTwoDays(id)
            },
            {_two.value=it }
        )

    }








    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }




    private val _topDetails=MutableLiveData<CoinTopDetails>()
    val topDetails: LiveData<CoinTopDetails>
        get()=_topDetails

    fun getcoinTopDetails(id:String){
        job= Coroutines.ioThenMain(
            { coinDetailsRepository.getCoinTopDetails(id)
            },
            {_topDetails.value=it }
        )

    }


}