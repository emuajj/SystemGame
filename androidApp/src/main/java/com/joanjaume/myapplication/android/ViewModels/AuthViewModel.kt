package com.joanjaume.myapplication.android.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // States for UI
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    // Login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val result = auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = AuthState.Success(result.user)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    // Register
    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                _authState.value = AuthState.Success(result.user)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Registration failed")
            }
        }
    }
}

// State Management for Authentication
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: FirebaseUser?) : AuthState()
    data class Error(val message: String) : AuthState()
}
