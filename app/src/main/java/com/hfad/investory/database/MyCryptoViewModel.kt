package com.hfad.investory.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyCryptoViewModel(private val dao: MyCryptoDao): ViewModel() {
    fun insertCrypto(crypto: MyCrypto) {
        viewModelScope.launch {
            dao.insert(crypto)
        }
    }
}