package com.hfad.investory.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel: ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String) {
        if (email.isBlank() || password.length < 6){
            _authState.value = AuthState.Error("Enter minimum 6 symbols")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _authState.value = if (task.isSuccessful) {
                    AuthState.Success("Registration passed successfully")
                } else {
                    AuthState.Error(task.exception?.message ?: "Registration ERROR")
                }
            }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.length < 6){
            _authState.value = AuthState.Error("Enter minimum 6 symbols")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _authState.value = if (task.isSuccessful) {
                    AuthState.Success("Login completed")
                } else {
                    AuthState.Error(task.exception?.message ?: "Login ERROR")
                }
            }
    }
}


sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val error: String) : AuthState()
}
