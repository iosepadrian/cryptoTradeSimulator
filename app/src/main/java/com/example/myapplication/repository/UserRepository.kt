package com.example.myapplication.repository

import android.app.Application
import android.util.Log
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.UserDao
import com.example.myapplication.utils.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(application: Application) {

    private var userDao: UserDao

    init {
        val db = AppDatabase.getDatabase(application)
        userDao = db.userDao()
    }

    fun getUser(): User {
        return userDao.load()
    }

    fun getUserAsync(): Flow<User> {
        return userDao.loadAsync()
    }

    fun getAllUsers():List<User>{
        return userDao.loadAll()
    }

    fun insert(user : User) {
        print(Log.v("AdiTag","user inserted"))
        userDao.insert(user)
    }

    fun delete(user: User?){
        print(Log.v("AdiTag","user deleted"))
        userDao.delete(user)
    }




}