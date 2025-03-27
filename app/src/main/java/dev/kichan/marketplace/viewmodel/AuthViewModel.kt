package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.kichan.marketplace.model.repository.AuthRepositoryImpl


class AuthViewModel(application: Application = Application()) : AndroidViewModel(application) {
    // application.applicationContext를 통해 Context를 전달합니다.
    private val authRepository = AuthRepositoryImpl(application.applicationContext)

    fun logout(
        onSuccess: () -> Unit,
        onFail: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                authRepository.logout()
                onSuccess()
            } catch (e: Exception) {
                onFail(e)
            }
        }
    }
}