package dev.kichan.marketplace.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.getAuthToken
import dev.kichan.marketplace.model.removeAuthToken
import dev.kichan.marketplace.model.saveAuthToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoggedIn: Boolean = false,
    val authToken: String? = null
)

class AuthViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
    val uiState = _uiState.asStateFlow()

    fun checkLoginStatus(context: Context) {
        viewModelScope.launch {
            getAuthToken(context).collect { token ->
                _uiState.value = AuthState(isLoggedIn = token != null, authToken = token)
            }
        }
    }

    fun login(context: Context, token: String) {
        viewModelScope.launch {
            saveAuthToken(context, token)
            _uiState.value = AuthState(isLoggedIn = true, authToken = token)
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            removeAuthToken(context)
            _uiState.value = AuthState(isLoggedIn = false, authToken = null)
        }
    }
}