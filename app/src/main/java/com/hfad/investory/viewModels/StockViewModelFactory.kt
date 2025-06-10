package com.hfad.investory.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.investory.database.MyStockDao

class StockViewModelFactory(private val dao: MyStockDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            return StockViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}