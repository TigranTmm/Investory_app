package com.hfad.investory.database

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.hfad.investory.BuildConfig
import com.hfad.investory.R
import com.hfad.investory.databinding.FragmentAddStockBinding
import com.hfad.investory.viewModels.StockMHomeViewModel

class AddStockFragment : Fragment() {
    private var _binding: FragmentAddStockBinding? = null
    private val binding get() = _binding!!

    // get model
    private val stockHomeViewModel: StockMHomeViewModel by activityViewModels()
    private lateinit var myStockViewModel: MyStockViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentAddStockBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // Dao
        val dao = AppDatabase.getInstance(requireContext()).myStockDao()
        val factory = MyStockFactory(dao)
        myStockViewModel = ViewModelProvider(this, factory)[MyStockViewModel::class.java]

        // Saving coin
        binding.saveButton.setOnClickListener{
            val symbolInput = binding.symbolInput.text.toString().trim().uppercase()
            val amountInput = binding.amountInput.text.toString().trim()

            if (symbolInput.isEmpty() || amountInput.isEmpty()) {
                Toast.makeText(context, "Enter both field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountInput.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(context, "Enter correct amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener

            val myStock = MyStock(
                iconUrl = "",
                symbol = symbolInput,
                amount = amount,
                pricePerCoin = 100.0,
                totalValue = amount * 100.0,
                userId = userId
            )

            myStockViewModel.insertCrypto(myStock)
            Toast.makeText(context, "Asset added", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
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
