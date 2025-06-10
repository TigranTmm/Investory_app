package com.hfad.investory.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyCryproFactory(private val dao: MyCryptoDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyCryptoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyCryptoViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}