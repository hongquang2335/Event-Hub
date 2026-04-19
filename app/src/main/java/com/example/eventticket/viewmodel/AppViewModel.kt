package com.example.eventticket.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface AuthState {
    data object Splash : AuthState
    data object Welcome : AuthState
    data object SignedOut : AuthState
    data class SignedIn(val userName: String) : AuthState
}

class AppViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Splash)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        _authState.value = AuthState.Welcome
    }

    fun continueAsGuest() {
        _authState.value = AuthState.SignedIn("FanZone Guest")
    }

    fun showLogin() {
        _authState.value = AuthState.SignedOut
    }

    fun login(email: String, password: String) {
        _authState.value = AuthState.SignedIn(email.substringBefore('@').ifBlank { "FanZone User" })
    }

    fun register(name: String, email: String, password: String) {
        _authState.value = AuthState.SignedIn(name.ifBlank { email.substringBefore('@') })
    }

    fun signOut() {
        _authState.value = AuthState.Welcome
    }
}
