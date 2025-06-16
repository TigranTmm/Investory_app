package com.hfad.investory.database

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.hfad.investory.API.CryptoCoin
import com.hfad.investory.API.RetrofitInstance
import com.hfad.investory.R
import com.hfad.investory.databinding.FragmentAddCryptoBinding
import com.hfad.investory.viewModels.CryptoHomeViewModel
import kotlinx.coroutines.launch

class AddCryptoFragment : Fragment() {
    private var _binding: FragmentAddCryptoBinding? = null
    private val binding get() = _binding!!

    // get model
    private val cryptoHomeViewModel: CryptoHomeViewModel by activityViewModels()
    private lateinit var myCryptoViewModel: MyCryptoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View binding
        _binding = FragmentAddCryptoBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // View Model
        cryptoHomeViewModel.getCoins()

        // Dao
        val dao = AppDatabase.getInstance(requireContext()).myCryptoDao()
        val factory = MyCryproFactory(dao)
        myCryptoViewModel = ViewModelProvider(this, factory)[MyCryptoViewModel::class.java]

        // Exit
        binding.exitButton.setOnClickListener {
            findNavController().navigateUp()
        }

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

            // Coin searching
            val coins = cryptoHomeViewModel.coins.value

            if (coins.isNullOrEmpty()) {
                Toast.makeText(context, "Loaded ERROR", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val coin = coins.find { it.symbol.equals(symbolInput, ignoreCase = true) }

            if (coin == null) {
                Toast.makeText(context, "Coin not founded", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener

            val myCrypto = MyCrypto(
                iconUrl = coin.image,
                symbol = coin.symbol,
                amount = amount,
                pricePerCoin = coin.currentPrice,
                totalValue = amount * coin.currentPrice,
                userId = userId
            )

            myCryptoViewModel.insertCrypto(myCrypto)
            Toast.makeText(context, "Coin added", Toast.LENGTH_SHORT).show()
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
