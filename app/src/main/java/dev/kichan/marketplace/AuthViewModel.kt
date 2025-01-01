package dev.kichan.marketplace.ui.component.dev.kichan.marketplace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.MarketRepositoryImpl
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    //todo: DI 적용하기
    private val memberRepository = MemberRepositoryImpl()

    val member = MutableLiveData<LoginRes>()

    fun login(id : String, password : String, onSuccess : () -> Unit, onFail : () -> Unit) {
        viewModelScope.launch {
            val res = memberRepository.login(body = LoginReq(studentId = id, password = password))

            if(res.isSuccessful) {
                member.value = res.body()!!.response
                onSuccess()
            }
            else {
                onFail()
            }
        }
    }
}