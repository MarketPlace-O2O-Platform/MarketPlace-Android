package dev.kichan.marketplace.ui.component.dev.kichan.marketplace

import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.data.market.Market
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository.MarketRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {
    //todo: DI 적용하기
    private val memberRepository = MemberRepositoryImpl()
    private val marketRepository = MarketRepositoryImpl()


    ///////////////////////  회원  /////////////////////////

    // 회원 관련 데이터
    val member = MutableLiveData<LoginRes?>()

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
        onSuccess() // 로그아웃 성공 시 호출
        // 필요에 따라 실패 시 호출할 로직 추가
    }


    ///////////////////////  이벤트  /////////////////////////

    val top20Market = MutableLiveData<List<Market>>()
    val newEvent = MutableLiveData<List<Market>>()
    val myCuration = MutableLiveData<List<Market>>()

    fun getTop20Market() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = marketRepository.getTopFavoriteMarkets(lastPageIndex = 2, pageSize = 20)
            if(res.isSuccessful) {
                withContext(Dispatchers.Main){
                    top20Market.value = res.body()!!.response.markets
                }
            }
        }
    }

    fun getNewEvent() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = marketRepository.getLatestCoupon(
                memberId = member.value!!.studentId,
                lastPageIndex = null,
                lastCreateAt = null,
                pageSize = null
            )
            if(res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    newEvent.value = res.body()!!.response.markets
                }
            }
        }
    }

    fun getMyCuration() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = marketRepository.getMyFavoriteMarkets(memberId = member.value!!.studentId, lastPageIndex = 0, pageSize = 5)
            if(res.isSuccessful) {
                withContext(Dispatchers.Main){
                    myCuration.value = res.body()!!.response.markets
                }
            }
        }
    }
}