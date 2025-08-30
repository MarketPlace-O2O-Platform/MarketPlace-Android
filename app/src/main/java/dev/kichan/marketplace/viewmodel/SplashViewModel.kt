package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SplashUiState {
    object Loading : SplashUiState()
    object Authenticated : SplashUiState()
    object Unauthenticated : SplashUiState()
}

class SplashViewModel : ViewModel() {
    private val membersRepository = RepositoryProvider.provideMembersRepository()

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        checkTokenValidity()
    }

    private fun checkTokenValidity() {
        viewModelScope.launch {
            delay(1000) // Simulate a minimum splash time
            try {
                val response = membersRepository.getMember()
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = SplashUiState.Authenticated
                } else {
                    _uiState.value = SplashUiState.Unauthenticated
                }
            } catch (e: Exception) {
                _uiState.value = SplashUiState.Unauthenticated
            }
        }
    }
}
