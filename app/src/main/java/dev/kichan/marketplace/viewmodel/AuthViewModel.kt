package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.repository.AuthRepository
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    // application.applicationContext를 통해 Context를 전달합니다.
    private val authRepository = AuthRepository(application.applicationContext)

    fun logout(
        onSuccess: () -> Unit,
        onFail: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = authRepository.logout()
                if (result) {
                    onSuccess()
                } else {
                    onFail(Throwable("로그아웃 실패"))
                }
            } catch (e: Exception) {
                onFail(e)
            }
        }
    }
}