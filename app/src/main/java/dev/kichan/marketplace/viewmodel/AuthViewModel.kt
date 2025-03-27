package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.repository.AuthRepositoryImpl
import dev.kichan.marketplace.model.saveAuthToken
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject

sealed class LoginPageState {
    data class LoginPageIdle(val inputId: String, val inputPassword: String) : LoginPageState()
}

class AuthViewModel(private val application: Application = Application()) : AndroidViewModel(application) {
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
                    val errorBody = res.errorBody()?.string()
                    val message = JSONObject(errorBody ?: "{}").optString("message", "로그인 실패")
                    throw Exception(message)
                }

                val token = res.body()!!.response
                NetworkModule.updateToken(token)
                saveAuthToken(application.applicationContext, token)
                onSuccess()
            }
            catch (e: Exception) {
                Log.e("error", e.message.toString())
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