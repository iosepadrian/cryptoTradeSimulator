package com.example.myapplication.repository

import android.app.Application
import android.util.Log
import com.example.myapplication.data.data.model.Transaction
import com.example.myapplication.repository.database.TransactionDao
import com.example.myapplication.repository.database.TransactionDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(application: Application) {

    private var transactionDao: TransactionDao

    init {
        val db = TransactionDatabase.getDatabase(application)
        transactionDao = db.TransactionDao()
    }

    fun getTransactionAsync(): Flow<Transaction> {
        return transactionDao.loadAsync()
    }

    fun getAllTransactionsCoins(): Flow<List<Transaction>> {
        return transactionDao.loadAll()
    }

    fun insert(transaction : Transaction) {
        print(Log.v("AdiTag","transaction inserted"))
        transactionDao.insert(transaction)
    }

    fun delete(transaction: Transaction?){
        print(Log.v("AdiTag","transaction deleted"))
        transactionDao.delete(transaction)
    }

}