package com.hfad.investory.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyStockFactory(private val dao: MyStockDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyStockViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyStockViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}