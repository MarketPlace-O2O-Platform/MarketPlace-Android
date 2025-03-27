package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.app.Application
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.getAuthToken
import dev.kichan.marketplace.model.repository.AuthRepositoryImpl
import dev.kichan.marketplace.model.saveAuthToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import org.json.JSONObject

sealed class LoginUiState {
    data object Idle : LoginUiState()
    data object Loading : LoginUiState()
    data object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class AuthViewModel(private val application: Application = Application()) :AndroidViewModel(application) {
    private val authRepository = AuthRepositoryImpl(application.applicationContext)

    var loginState by mutableStateOf<LoginUiState>(LoginUiState.Idle)

    init {
        autoLogin()
    }

    fun login(
        id: String,
        password: String,
    ) {
        viewModelScope.launch {
            try {
                val body = LoginReq(id, password)
                val res = authRepository.login(body)

                if (!res.isSuccessful) {
                    val errorBody = res.errorBody()?.string()
                    val message = JSONObject(errorBody ?: "{}").optString("message", "로그인 실패")
                    throw Exception(message)
                }

                val token = res.body()!!.response
                NetworkModule.updateToken(token)
                saveAuthToken(application.applicationContext, token)

                loginState = LoginUiState.Success
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
                loginState = LoginUiState.Error(e.message.toString())
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

    private fun autoLogin() {
        viewModelScope.launch {
            getAuthToken(application.applicationContext).collect { token ->
                if (token.isNullOrBlank()) {
                    return@collect
                }

                Log.d("token", token)

                NetworkModule.updateToken(token)
                val res = authRepository.getMemberData()
                if (!res.isSuccessful) {
                    NetworkModule.updateToken(null)
                    return@collect
                }
                loginState = LoginUiState.Success
            }
        }
    }
}