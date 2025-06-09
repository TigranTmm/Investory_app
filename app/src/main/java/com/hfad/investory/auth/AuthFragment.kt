package com.hfad.investory.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.hfad.investory.R
import com.hfad.investory.databinding.FragmentAuthBinding
import com.hfad.investory.databinding.FragmentCryptoBinding
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var regiserButton: Button
    private lateinit var singInButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View bonding
        _binding = FragmentAuthBinding
            .inflate(inflater, container, false)
        val view = binding.root

        // View model
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Lateinit initialization
        emailInput = binding.emailInput
        passwordInput = binding.passwordInput
        regiserButton = binding.btnRegister
        singInButton = binding.btnLogin
        progressBar = binding.progressBar


        lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthState.Idle -> progressBar.visibility = View.GONE
                    is AuthState.Loading -> progressBar.visibility = View.VISIBLE
                    is AuthState.Success -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                    }
                    is AuthState.Error -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        regiserButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            viewModel.register(email, password)
        }

        singInButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            viewModel.login(email, password)
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
