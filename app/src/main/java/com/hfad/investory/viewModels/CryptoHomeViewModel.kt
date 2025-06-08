package com.hfad.investory.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.investory.API.CryptoCoin
import com.hfad.investory.API.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CryptoHomeViewModel : ViewModel() {
    private val _coins = MutableLiveData<List<CryptoCoin>>()
    val coins: LiveData<List<CryptoCoin>> = _coins

    fun getCoins() {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getTopCoins()
                _coins.postValue(result)
            } catch (e: Exception) {
                Log.e("CryptoViewModel", "Error: ${e.message}")
            }
        }
    }
}