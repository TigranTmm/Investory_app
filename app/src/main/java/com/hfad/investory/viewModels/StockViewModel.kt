package com.hfad.investory.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.investory.database.MyCrypto
import com.hfad.investory.database.MyCryptoDao
import com.hfad.investory.database.MyStock
import com.hfad.investory.database.MyStockDao
import kotlinx.coroutines.launch

class StockViewModel(private val dao: MyStockDao): ViewModel() {
    private val _stockList = MutableLiveData<List<MyStock>>()
    val stockList: LiveData<List<MyStock>> = _stockList

    fun loadUserStocks(userId: String) {
        viewModelScope.launch {
            val result = dao.getAllByUser(userId)
            _stockList.postValue(result)
        }
    }

    fun deleteActive(crypto: MyStock) {
        viewModelScope.launch {
            dao.delete(crypto)
            crypto.userId?.let { loadUserStocks(it) }
        }
    }
}