package dev.kichan.marketplace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {
    //todo: DI 적용하기
    private val memberRepository = MemberRepositoryImpl()


    val member = MutableLiveData<LoginRes?>(
        LoginRes(studentId = 202401598)
    )

    fun login(id: String, password: String, onSuccess: () -> Unit, onFail: () -> Unit) {
        viewModelScope.launch {
            val res = memberRepository.login(body = LoginReq(studentId = id, password = password))

            if (res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    member.value = res.body()!!.response
                    onSuccess()
                }
            } else {
                withContext(Dispatchers.Main) {
                    onFail() // 일반 함수로 유지
                }
            }
        }
    }


    fun logout(onSuccess: () -> Unit, onFail: () -> Unit) {
        member.value = null
        onSuccess()
    }
}