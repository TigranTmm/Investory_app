package com.hfad.investory

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.hfad.investory.databinding.FragmentCryptoBinding
import com.hfad.investory.viewModels.CryptoViewModel


class CryptoFragment : Fragment() {
    private var _binding: FragmentCryptoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CryptoViewModel
    private lateinit var pieChart: PieChart
    private lateinit var resView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentCryptoBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // View model
        viewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        viewModel.setData()

        // Lateinit initialization
        pieChart = binding.cryptoPieChart
        resView = binding.recCrypto


        /** Pie Chart **/
        val pieColors = listOf(
            ContextCompat.getColor(requireContext(), R.color.one),
            ContextCompat.getColor(requireContext(), R.color.two),
            ContextCompat.getColor(requireContext(), R.color.thee),
            ContextCompat.getColor(requireContext(), R.color.four),
            ContextCompat.getColor(requireContext(), R.color.five)
        )

        // Legend settings
        val legend = pieChart.legend
        legend.apply {
            isEnabled = true
            form = Legend.LegendForm.CIRCLE
            verticalAlignment = Legend.LegendVerticalAlignment.CENTER
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.VERTICAL
            setDrawInside(false)
            textColor = ContextCompat.getColor(requireContext(), R.color.legend_text)
            textSize = 16f
            yEntrySpace = 8f
            xOffset = 32f
            yOffset = -24f
        }

        // Chart settings
        pieChart.apply {
            description.isEnabled = false
            setDrawEntryLabels(false)
            setTransparentCircleAlpha(0)
            holeRadius = 75f
            setHoleColor(Color.TRANSPARENT)
            animateY(1500)
            setExtraOffsets(0f, 0f, 0f, 0f)
            extraLeftOffset = -60f
        }

        // Data setting
        viewModel.chartData.observe(viewLifecycleOwner) { chartData ->
            val dataSet = PieDataSet(chartData, "")
            dataSet.sliceSpace = 7f
            dataSet.selectionShift = 5f
            dataSet.colors = pieColors

            val data = PieData(dataSet)
            data.setDrawValues(false)

            pieChart.data = data

            pieChart.invalidate()
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}