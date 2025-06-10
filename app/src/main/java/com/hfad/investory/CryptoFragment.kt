package com.hfad.investory

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.ListFragment
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.hfad.investory.database.AppDatabase
import com.hfad.investory.database.DeleteCryptoFragment
import com.hfad.investory.databinding.FragmentCryptoBinding
import com.hfad.investory.viewModels.CryptoViewModel


class CryptoFragment : Fragment() {
    private var _binding: FragmentCryptoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CryptoViewModel
    private lateinit var pieChart: PieChart
    private lateinit var resView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentCryptoBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // View model
        val dao = AppDatabase.getInstance(requireContext()).myCryptoDao()
        val factory = CryptoViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, factory)[CryptoViewModel::class.java]

        // Lateinit initialization
        pieChart = binding.cryptoPieChart
        resView = binding.recCrypto
        fab = binding.fab

        // Adapter setting + userId
        resView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CryptoAdapter { coin ->
            val action = CryptoFragmentDirections
                .actionCryptoFragmentToDeleteCryptoFragment2(coin.id.toString())
            findNavController().navigate(action)
        }
        resView.adapter = adapter
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        /** FAB **/
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_cryptoFragment_to_addCryptoFragment)
        }

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
        viewModel.loadUserCryptos(userId)
        viewModel.cryptoList.observe(viewLifecycleOwner) { list ->
            Log.d("CryptoFragment", "cryptoList size = ${list.size}")
            adapter.submitList(list)

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
            dataSet.colors = pieColors

            val data = PieData(dataSet)
            data.setDrawValues(false)

            pieChart.data = data

            pieChart.invalidate()

            // Sum
            binding.total.text = "${"%.2f".format(list.sumOf { it.totalValue })} $"
        }

        // BottomNav visibility
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.VISIBLE

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}