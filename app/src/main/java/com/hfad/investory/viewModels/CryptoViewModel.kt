package com.hfad.investory.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry

class CryptoViewModel: ViewModel() {
    // Chart data
    private val _chartData = MutableLiveData<List<PieEntry>>()
    val chartData: LiveData<List<PieEntry>> = _chartData

    // Setting pie data
    fun setData() {
        _chartData.value = listOf(
            PieEntry(1852f, "BTC"),
            PieEntry(1176f, "ETH"),
            PieEntry(836f, "APT"),
            PieEntry(836f, "STRK"),
            PieEntry(125f, "Others")
        )
    }
}