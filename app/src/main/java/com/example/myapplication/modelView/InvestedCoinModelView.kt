package com.example.myapplication.modelView

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.data.data.model.InvestedCoin
import com.example.myapplication.repository.InvestedCoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InvestedCoinModelView @Inject constructor(
    application: Application,
    private val investedCoinRepository: InvestedCoinRepository
) : AndroidViewModel(application) {


    private var _investedcoin = MutableLiveData<List<InvestedCoin>>()
    var investedcoin: LiveData<List<InvestedCoin>> =_investedcoin

    init {
        viewModelScope.launch {
            investedcoin = investedCoinRepository.getAllInvestedCoins().asLiveData()
        }
    }

    fun insertInvestedCoin(inv: InvestedCoin){
        investedCoinRepository.insert(inv)
    }
    fun deleteInvestedCoin(inv: InvestedCoin?){
        investedCoinRepository.delete(inv)
    }

    /*fun loadAllFavCoins():List<FavCoin>{
        return favCoinRepository.getAllFavCoins()
    }*/

}