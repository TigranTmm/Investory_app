package com.hfad.investory.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.hfad.investory.database.MyCrypto
import com.hfad.investory.database.MyCryptoDao
import kotlinx.coroutines.launch

class CryptoViewModel(private val dao: MyCryptoDao): ViewModel() {
    private val _cryptoList = MutableLiveData<List<MyCrypto>>()
    val cryptoList: LiveData<List<MyCrypto>> = _cryptoList

    fun loadUserCryptos(userId: String) {
        viewModelScope.launch {
            val result = dao.getAllByUser(userId)
            _cryptoList.postValue(result)
        }
    }

    fun deleteCrypto(crypto: MyCrypto) {
        viewModelScope.launch {
            dao.delete(crypto)
            crypto.userId?.let { loadUserCryptos(it) }
        }
    }
}