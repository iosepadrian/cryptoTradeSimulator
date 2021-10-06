package com.example.myapplication.modelView

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.utils.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {


    private var _user = MutableLiveData<User>()
    var user: LiveData<User> =_user

    init {
        viewModelScope.launch {
            user = userRepository.getUserAsync().asLiveData()
        }
    }

    fun insertUser(user: User){
        userRepository.insert(user)
    }
    fun deleteUser(user: User?){
        userRepository.delete(user)
    }

    fun loadUser() : User {
         return userRepository.getUser()
    }

    fun loadAllUsers():List<User>{
        return userRepository.getAllUsers()
    }

}
