package dev.kichan.marketplace.ui.component.dev.kichan.marketplace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.data.market.Market
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.MarketRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    //todo: DI 적용하기
    private val memberRepository = MemberRepositoryImpl()
    private val marketRepository = MarketRepositoryImpl()

    val member = MutableLiveData<LoginRes>()

    val top20Market = MutableLiveData<List<Market>>()

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

    fun getTop20Market() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = marketRepository.getTopFavoriteMarkets(lastPageIndex = 2, pageSize = 20)
            if(res.isSuccessful) {
                top20Market.value = res.body()!!.response.markets
            }
        }
    }
}