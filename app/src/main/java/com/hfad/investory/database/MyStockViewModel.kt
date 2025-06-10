package com.hfad.investory.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyStockViewModel(private val dao: MyStockDao): ViewModel() {
    fun insertCrypto(stock: MyStock) {
        viewModelScope.launch {
            dao.insert(stock)
        }
    }
}