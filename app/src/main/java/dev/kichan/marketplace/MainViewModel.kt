package dev.kichan.marketplace.ui.component.dev.kichan.marketplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.MarketRepositoryImpl
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    //todo: DI 적용하기
    private val memberRepository = MemberRepositoryImpl()
    private val marketRepository = MarketRepositoryImpl()

    val state = MutableLiveData(State.Success)

    val member = MutableLiveData<LoginRes>()

    fun login(id : String, password : String) {
        state.value = State.Loading
        viewModelScope.launch {
            val res = memberRepository.login(body = LoginReq(studentId = id, password = password))

            if(res.isSuccessful) {
                member.value = res.body()!!.response
                state.value = State.Success
            }
            else {
                state.value = State.Erroe
            }
        }
    }
}