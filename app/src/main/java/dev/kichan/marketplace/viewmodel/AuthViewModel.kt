package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow

sealed class LoginPageState {
    data class LoginPageIdle(val inputId: String, val inputPassword: String) : LoginPageState()
}

class AuthViewModel(application: Application = Application()) : AndroidViewModel(application) {
    private val authRepository = AuthRepositoryImpl(application.applicationContext)

    val loginPageState : MutableStateFlow<LoginPageState> = MutableStateFlow(LoginPageState.LoginPageIdle(
        inputId = "",
        inputPassword = ""
    ))

    fun login(
        id: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val body = LoginReq(id, password)
                val res = authRepository.login(body)
                if(!res.isSuccessful) {
                    throw Exception()
                }
                val token = res.body()!!.response
                onSuccess()
            }
            catch (e: Exception) {
                onFail()
            }
        }
    }

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