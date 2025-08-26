package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.LoginReq
import dev.kichan.marketplace.model.data.MemberLoginRes
import dev.kichan.marketplace.model.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

sealed class LoginUiState {
    data object Idle : LoginUiState()
    data object Loading : LoginUiState()
    data class Success(val member: MemberLoginRes) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

sealed class LoginNavigationEvent {
    object PopBackStack : LoginNavigationEvent()
    object NavigateToMain : LoginNavigationEvent()
}

class LoginViewModel(
    application: Application,
    private val memberRepository: MemberRepository = MemberRepositoryImpl(NetworkModule.getService(MemberService::class.java))
) : AndroidViewModel(application) {

    var loginState by mutableStateOf<LoginUiState>(LoginUiState.Idle)

    private val _navigationEvent = MutableSharedFlow<LoginNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        // autoLogin() // Re-evaluate or remove auto-login logic
    }

    private suspend fun getMemberData(): MemberLoginRes {
        val res = memberRepository.getMember()

        if (!res.isSuccessful) {
            val errorBody = res.errorBody()?.string()
            val message = JSONObject(errorBody ?: "{}").optString("message", "회원 정보 조회 실패")
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
                val body = MemberLoginReq(studentId = id, password = password)
                val res = memberRepository.loginMember(body)

                if (!res.isSuccessful) {
                    val errorBody = res.errorBody()?.string()
                    val message = JSONObject(errorBody ?: "{}").optString("message", "로그인 실패")
                    throw Exception(message)
                }

                val token = res.body()!!.response // Assuming response is the token string
                NetworkModule.updateToken(token)
                // saveAuthToken(application.applicationContext, token) // Old token saving, re-evaluate

                val memberData = getMemberData()
                loginState = LoginUiState.Success(memberData)
                _navigationEvent.emit(LoginNavigationEvent.PopBackStack)
                _navigationEvent.emit(LoginNavigationEvent.NavigateToMain)
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
                // memberRepository.logout() // No direct logout in new MemberRepository
                onSuccess()
            } catch (e: Exception) {
                onFail(e)
            }
        }
    }

    // Re-evaluate or remove auto-login logic
    /*
    private fun autoLogin() {
        viewModelScope.launch {
            getAuthToken(application.applicationContext).collect { token ->
                if (token.isNullOrBlank()) {
                    loginState = LoginUiState.Error("")
                    return@collect
                }

                Log.d("token", token)

                NetworkModule.updateToken(token)
                val res = memberRepository.getMember()
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
    */

    fun saveFcmToken(token: String) {
        viewModelScope.launch {
            // val res = withContext(Dispatchers.IO) { memberRepository.permitFcmToken(MemberFcmReq(fcmToken = token)) }
            // if (!res.isSuccessful) { /* throw Exception("FCM 토큰 저장 실패") */ }
        }
    }

    fun refershMemberData() {
        viewModelScope.launch {
            val res = getMemberData()
            loginState = LoginUiState.Success(res)
        }
    }
}