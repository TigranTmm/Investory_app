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
import com.hfad.investory.databinding.FragmentCryptoBinding
import com.hfad.investory.databinding.FragmentDeleteCryptoBinding
import kotlinx.coroutines.launch

class DeleteCryptoFragment : Fragment() {
    private var _binding: FragmentDeleteCryptoBinding? = null
    private val binding get() = _binding!!

    private val args: DeleteCryptoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentDeleteCryptoBinding
            .inflate(inflater, container, false)
        val view = binding.root

        val itemId = args.id
        val dao = AppDatabase.getInstance(requireContext()).myCryptoDao()

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