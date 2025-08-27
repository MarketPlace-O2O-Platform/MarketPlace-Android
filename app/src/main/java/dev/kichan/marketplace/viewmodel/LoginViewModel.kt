package dev.kichan.marketplace.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MemberLoginReq
import dev.kichan.marketplace.model.saveAuthToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Authenticated : LoginUiState()
    object Unauthenticated : LoginUiState()
    data class Error(val message: String) : LoginUiState()
    data class LoginInputState(
        val studentId: String = "",
        val password: String = "",
        val selectedSchool: String = ""
    )
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val membersRepository = RepositoryProvider.provideMembersRepository()

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _loginInputState = MutableStateFlow(LoginUiState.LoginInputState())
    val loginInputState = _loginInputState.asStateFlow()

    fun setStudentId(id: String) {
        _loginInputState.update { it.copy(studentId = id) }
    }

    fun setPassword(password: String) {
        _loginInputState.update { it.copy(password = password) }
    }

    fun setSelectedSchool(school: String) {
        _loginInputState.update { it.copy(selectedSchool = school) }
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            delay(2000) // Simulate network delay
            // In a real app, check for saved token here
            val tokenExists = false // Replace with actual token check
            if (tokenExists) {
                _loginState.value = LoginUiState.Authenticated
            } else {
                _loginState.value = LoginUiState.Unauthenticated
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            try {
                val response = membersRepository.loginMember(
                    MemberLoginReq(
                        loginInputState.value.studentId,
                        loginInputState.value.password
                    )
                )
                if (response.isSuccessful) {
                    val token = response.body()?.response
                    if (token != null) {
                        // Save token here
                        _loginState.value = LoginUiState.Authenticated
                    } else {
                        _loginState.value = LoginUiState.Error("토큰이 없습니다.")
                    }
                } else {
                    _loginState.value = LoginUiState.Error("로그인에 실패했습니다.")
                }
            } catch (e: Exception) {
                _loginState.value = LoginUiState.Error("로그인 중 오류가 발생했습니다: ${e.message}")
            }
        }
    }
}