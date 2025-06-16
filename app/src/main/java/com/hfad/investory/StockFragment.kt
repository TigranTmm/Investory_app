package com.hfad.investory

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.hfad.investory.database.AppDatabase
import com.hfad.investory.database.MyCrypto
import com.hfad.investory.database.MyStock
import com.hfad.investory.databinding.FragmentStockBinding
import com.hfad.investory.viewModels.StockViewModel
import com.hfad.investory.viewModels.StockViewModelFactory
import java.text.NumberFormat
import java.util.Locale


class StockFragment : Fragment() {
    private var _binding: FragmentStockBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: StockViewModel
    private lateinit var pieChart: PieChart
    private lateinit var resView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var checkBox: MaterialCheckBox

    private var currentList = emptyList<MyStock>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentStockBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // View model
        val dao = AppDatabase.getInstance(requireContext()).myStockDao()
        val factory = StockViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[StockViewModel::class.java]

        // Lateinit initialization
        pieChart = binding.stockPieChart
        resView = binding.recStock
        fab = binding.fab
        checkBox = binding.sortButton

        /** RecView **/
        resView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = StockAdapter { coin ->
            deleteDialog(coin)
        }
        resView.adapter = adapter

        /** FAB **/
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_stockFragment_to_addStockFragment)
        }

        /** CheckBox **/
        checkBox.setOnCheckedChangeListener { _, _ ->
            sortAdapterList(currentList)
        }

        /** Pie Chart **/
        pieChartSettings()

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        viewModel.loadUserStocks(userId)

        viewModel.stockList.observe(viewLifecycleOwner) { list ->
            currentList = list
            sortAdapterList(currentList)
            updateData(currentList)
        }

        // BottomNav visibility
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.VISIBLE

        return view
    }


    /** List sorting **/
    private fun sortAdapterList(list: List<MyStock>) {
        val isChecked = checkBox.isChecked
        val sorted = if (isChecked)
            list.sortedBy { it.totalValue }
        else list.sortedByDescending { it.totalValue }

        (binding.recStock.adapter as StockAdapter).submitList(sorted)
    }


    /** Data setting **/
    private fun updateData(list: List<MyStock>) {
        // PieChart data preparing
        val sortedList = list.sortedByDescending { it.totalValue }
        val top4 = sortedList.take(4)
        val others = sortedList.drop(4)

        val topEntries = top4.map {
            PieEntry(it.totalValue.toFloat(), it.symbol.uppercase())
        }
        val othersTotal = others.sumOf { it.totalValue }

        val finalEntries = if (othersTotal > 0) {
            topEntries + PieEntry(othersTotal.toFloat(), "Others")
        } else {
            topEntries
        }

        // PieChart data setting
        val dataSet = PieDataSet(finalEntries, "")
        dataSet.sliceSpace = 7f
        dataSet.selectionShift = 5f

        val pieColors = listOf(
            ContextCompat.getColor(requireContext(), R.color.one),
            ContextCompat.getColor(requireContext(), R.color.two),
            ContextCompat.getColor(requireContext(), R.color.thee),
            ContextCompat.getColor(requireContext(), R.color.four),
            ContextCompat.getColor(requireContext(), R.color.five)
        )
        dataSet.colors = pieColors

        val data = PieData(dataSet)
        data.setDrawValues(false)

        pieChart.data = data

        pieChart.invalidate()

        // Sum
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        binding.total.text = formatter.format(list.sumOf { it.totalValue })
    }


    /** PieChart view settings **/
    private fun pieChartSettings() {
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
    }


    /** Delete asset **/
    private fun deleteDialog(item: MyStock) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.delete_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.delete_button).setOnClickListener {
            viewModel.deleteActive(item)
            dialog.dismiss()
            Toast.makeText(context, "${item.symbol} was deleted", Toast.LENGTH_SHORT).show()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}