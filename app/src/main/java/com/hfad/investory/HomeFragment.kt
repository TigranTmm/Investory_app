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
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.hfad.investory.databinding.FragmentHomeBinding
import com.hfad.investory.viewModels.HomeViewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    lateinit var pieChart: PieChart
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: PageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentHomeBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // View model
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.setData()
        viewModel.setCenterText()

        // Lateinit initialization
        pieChart = binding.homePieChart
        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2
        adapter = PageAdapter(childFragmentManager, lifecycle)

        /** Pie chart **/
        val pieColors = listOf(
            ContextCompat.getColor(requireContext(), R.color.one),
            ContextCompat.getColor(requireContext(), R.color.two)
        )

        // Legend settings
        val legend = pieChart.legend
        legend.apply {
            isEnabled = true
            form = Legend.LegendForm.CIRCLE
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            orientation = Legend.LegendOrientation.HORIZONTAL
            setDrawInside(false)
            textColor = ContextCompat.getColor(requireContext(), R.color.legend_text)
            textSize = 16f
            xEntrySpace = 32f
            yEntrySpace = 16f
        }

        // Chart settings
        pieChart.apply {
            description.isEnabled = false
            setDrawEntryLabels(false)
            setCenterTextSize(32f)
            setCenterTextColor(ContextCompat.getColor(context, R.color.b_n_item))
            setTransparentCircleAlpha(0)
            holeRadius = 85f
            setHoleColor(Color.TRANSPARENT)
            setCenterTextSize(32f)
            setCenterTextTypeface(Typeface.DEFAULT_BOLD)
            animateY(1500)
        }

        // Data setting
        viewModel.chartData.observe(viewLifecycleOwner){ chartData ->
            val dataSet = PieDataSet(chartData, "")
            dataSet.sliceSpace = 10f
            dataSet.selectionShift = 5f
            dataSet.colors = pieColors

            val data = PieData(dataSet)
            data.setDrawValues(false)

            pieChart.data = data

            pieChart.invalidate()
        }

        // Center text setting
        viewModel.centerText.observe(viewLifecycleOwner) { centerText ->
            pieChart.centerText = centerText
        }


        /** View pager 2 **/
        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager2.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}