package com.hfad.investory

import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeViewModel: ViewModel() {
    // Chart data
    private val _chartData = MutableLiveData<List<PieEntry>>()
    val chartData: LiveData<List<PieEntry>> = _chartData

    // Center text
    private val _centerText = MutableLiveData<String>()
    val centerText: LiveData<String> = _centerText

    // Setting pie data
    fun setData() {
        _chartData.value = listOf(
            PieEntry(2362f, "Crypto"),
            PieEntry(7824f, "Stock Market")
        )
    }

    // Setting center text
    fun setCenterText() {
        _centerText.value = "10,125 $"
    }
}