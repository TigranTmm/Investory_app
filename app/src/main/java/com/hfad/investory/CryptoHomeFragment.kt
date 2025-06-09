package com.hfad.investory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hfad.investory.databinding.FragmentCryptoHomeBinding
import com.hfad.investory.viewModels.CryptoHomeViewModel

class CryptoHomeFragment : Fragment() {
    private var _binding: FragmentCryptoHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CryptoHomeViewModel
    private lateinit var resView: RecyclerView
    private lateinit var cryproAdapter: CryptoHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentCryptoHomeBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // View model
        viewModel = ViewModelProvider(this)[CryptoHomeViewModel::class.java]

        // RecyclerView + Adapter
        resView = binding.recCryptoHome
        cryproAdapter = CryptoHomeAdapter()
        resView.adapter = cryproAdapter
        resView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCoins()
        viewModel.coins.observe(viewLifecycleOwner) { coins ->
            cryproAdapter.submitList(coins)
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
