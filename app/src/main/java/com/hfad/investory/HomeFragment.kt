package com.hfad.investory

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.fonts.Font
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.hfad.investory.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view binding
        _binding = FragmentHomeBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // pie chart
        val pieChart = binding.homePieChart

        val pieValues = listOf(
            PieEntry(2362f, "Crypto"),
            PieEntry(7824f, "Stock Market")
        )

        val dataSet = PieDataSet(pieValues, "")
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        dataSet.sliceSpace = 10f
        dataSet.selectionShift = 5f
        dataSet.colors = listOf(
            ContextCompat.getColor(context, R.color.one),
            ContextCompat.getColor(context, R.color.two)
        )

        val data = PieData(dataSet)
        data.setDrawValues(false)

        val legend = pieChart.legend
        legend.isEnabled = true
        legend.form = Legend.LegendForm.CIRCLE
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
        legend.textColor = ContextCompat.getColor(context, R.color.legend_text)
        legend.textSize = 16f
        legend.xEntrySpace = 32f
        legend.yEntrySpace = 16f

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.centerText = "10,186 $"
        pieChart.setCenterTextSize(32f)
        pieChart.setCenterTextColor(ContextCompat.getColor(context, R.color.white))
        pieChart.setTransparentCircleAlpha(0)
        pieChart.holeRadius = 85f
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.setCenterTextSize(32f)
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart.animateY(1500)

        pieChart.invalidate()



        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}