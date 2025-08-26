package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MemberLoginReq
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class LoginEvent {
    data class Success(val token: String) : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val membersRepository = RepositoryProvider.provideMembersRepository()

    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    fun login(studentId: String, password: String) {
        viewModelScope.launch {
            try {
                val response = membersRepository.loginMember(MemberLoginReq(studentId, password))
                if (response.isSuccessful) {
                    val token = response.body()?.response
                    if (token != null) {
                        _loginEvent.emit(LoginEvent.Success(token))
                    } else {
                        _loginEvent.emit(LoginEvent.Error("토큰이 없습니다."))
                    }
                } else {
                    _loginEvent.emit(LoginEvent.Error("로그인에 실패했습니다."))
                }
            } catch (e: Exception) {
                _loginEvent.emit(LoginEvent.Error("로그인 중 오류가 발생했습니다: ${e.message}"))
            }
        }
    }
}