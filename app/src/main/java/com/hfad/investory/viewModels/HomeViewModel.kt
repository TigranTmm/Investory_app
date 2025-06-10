package com.hfad.investory.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry

class HomeViewModel: ViewModel() {
    // Pie data
    private val _chartData = MutableLiveData<List<PieEntry>>()
    val chartData: LiveData<List<PieEntry>> = _chartData

    // Center text
    private val _centerText = MutableLiveData<String>()
    val centerText: LiveData<String> = _centerText

    // Update data
    fun updatePortfolio(cryptoSum: Double, stockSum: Double) {
        _chartData.value = listOf(
            PieEntry(cryptoSum.toFloat(), "Crypto"),
            PieEntry(stockSum.toFloat(), "Stock Market")
        )

        val total = cryptoSum + stockSum
        _centerText.value = "${"%.2f".format(total)} $"
    }
}