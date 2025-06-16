package com.hfad.investory.database

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hfad.investory.R
import com.hfad.investory.databinding.FragmentDeleteStockBinding
import kotlinx.coroutines.launch

class DeleteStockFragment : Fragment() {
    private var _binding: FragmentDeleteStockBinding? = null
    private val binding get() = _binding!!

    private val args: DeleteStockFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentDeleteStockBinding
            .inflate(inflater, container, false)
        val view = binding.root

        val itemId = args.id
        val dao = AppDatabase.getInstance(requireContext()).myStockDao()

        binding.deleteButton.setOnClickListener {
            lifecycleScope.launch {
                val item = dao.getById(itemId.toInt())
                item?.let {
                    dao.delete(it)
                    findNavController().navigateUp()
                }
            }
        }

        // BottomNav visibility
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}