package com.example.myapplication.modelView

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.data.data.model.Favcoin
import com.example.myapplication.repository.FavCoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavCoinModelView @Inject constructor(
    application: Application,
    private val favCoinRepository: FavCoinRepository
) : AndroidViewModel(application) {


    private var _favcoin = MutableLiveData<List<Favcoin>>()
    var favcoin: LiveData<List<Favcoin>> =_favcoin

    init {
        viewModelScope.launch {
            favcoin = favCoinRepository.getAllFavCoins().asLiveData()
        }
    }

    fun insertFavCoin(fav: Favcoin){
        favCoinRepository.insert(fav)
    }
    fun deleteFavCoin(fav: Favcoin?){
        favCoinRepository.delete(fav)
    }

    /*fun loadAllFavCoins():List<FavCoin>{
        return favCoinRepository.getAllFavCoins()
    }*/

}
