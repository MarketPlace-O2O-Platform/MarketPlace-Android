package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.github.javafaker.Bool
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.data.login.MemberLoginRes
import dev.kichan.marketplace.model.getAuthToken
import dev.kichan.marketplace.model.repository.AuthRepositoryImpl
import dev.kichan.marketplace.model.saveAuthToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

sealed class LoginUiState {
    data object Idle : LoginUiState()
    data object Loading : LoginUiState()
    data class Success(val member: MemberLoginRes) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class AuthViewModel(private val application: Application = Application()) :
    AndroidViewModel(application) {
    private val authRepository = AuthRepositoryImpl(application.applicationContext)

    var loginState by mutableStateOf<LoginUiState>(LoginUiState.Idle)

    init {
        autoLogin()
    }

    private suspend fun getMemberData(): MemberLoginRes {
        val res = authRepository.getMemberData()

        if (!res.isSuccessful) {
            val errorBody = res.errorBody()?.string()
            val message = JSONObject(errorBody ?: "{}").optString("message", "로그인 실패")
            throw Exception(message)
        }

        return res.body()!!.response
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

                val memberData = getMemberData()
                loginState = LoginUiState.Success(memberData)
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
                    loginState = LoginUiState.Error("")
                    return@collect
                }

                Log.d("token", token)

                NetworkModule.updateToken(token)
                val res = authRepository.getMemberData()
                if (!res.isSuccessful) {
                    NetworkModule.updateToken(null)
                    loginState = LoginUiState.Error("로그인 실패")
                    return@collect
                }
                val memberData = getMemberData()
                loginState = LoginUiState.Success(memberData)
            }
        }
    }

    fun saveFcmToken(token: String) {
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                authRepository.saveFCMToken(token)
            }

            if (!res.isSuccessful) {
//                throw Exception("FCM 토큰 저장 실패")
            }
        }
    }

    fun refershMemberData() {
        viewModelScope.launch {
            val res = getMemberData()

            loginState = LoginUiState.Success(res)
        }
    }
}