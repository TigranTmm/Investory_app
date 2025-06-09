package com.hfad.investory.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.investory.API.RetrofitInstance2
import com.hfad.investory.API.StockCoins
import kotlinx.coroutines.launch

class StockMHomeViewModel: ViewModel() {
    private val _actives = MutableLiveData<List<StockCoins>>()
    val actives: LiveData<List<StockCoins>> = _actives

    fun getCoins(apiKey: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance2.api.getTopStocks(apikey = apiKey)
                _actives.postValue(result)
            } catch (e: Exception) {
                Log.e("StockMViewModel", "Error: ${e.message}")
            }
        }
    }

}