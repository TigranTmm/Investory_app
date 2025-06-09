package com.hfad.investory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hfad.investory.databinding.FragmentStockMHomeBinding
import com.hfad.investory.viewModels.StockMHomeViewModel

class StockMHomeFragment : Fragment() {
    private var _binding: FragmentStockMHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: StockMHomeViewModel
    lateinit var resView: RecyclerView
    lateinit var stockAdapter: StockMHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentStockMHomeBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // View model
        viewModel = ViewModelProvider(this)[StockMHomeViewModel::class.java]

        // RecyclerView + Adapter
        resView = binding.recStockHome
        stockAdapter = StockMHomeAdapter()
        resView.adapter = stockAdapter
        resView.layoutManager = LinearLayoutManager(requireContext())


        val apiKey = com.hfad.investory.BuildConfig.API_KEY
        viewModel.getCoins(apiKey)
        viewModel.actives.observe(viewLifecycleOwner) { actives ->
            stockAdapter.submitList(actives)
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
