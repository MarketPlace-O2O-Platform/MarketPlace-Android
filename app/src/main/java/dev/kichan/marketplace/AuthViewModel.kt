package dev.kichan.marketplace.ui.component.dev.kichan.marketplace

import LargeCategory
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.login.LoginReq
import dev.kichan.marketplace.model.data.login.LoginRes
import dev.kichan.marketplace.model.data.market.Market
import dev.kichan.marketplace.model.repository.MemberRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.TopLatestCoupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.model.repository.MarketRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {
    //todo: DI 적용하기
    private val memberRepository = MemberRepositoryImpl()
    private val marketRepository = MarketRepositoryImpl()


    ///////////////////////  회원  /////////////////////////

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
        onSuccess() // 로그아웃 성공 시 호출
        // 필요에 따라 실패 시 호출할 로직 추가
    }


    ///////////////////////  이벤트  /////////////////////////

    val top20Coupon = MutableLiveData<List<TopLatestCoupon>>()
    val newEvent = MutableLiveData<List<TopLatestCoupon>>()
    val myCuration = MutableLiveData<List<Market>>()
    val detailMarket = MutableLiveData<MarketDetailRes>()
    val allMarket = MutableLiveData<List<Market>>()

    fun getTop20Market() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = marketRepository.getTopLatestCoupons(
                memberId = member.value!!.studentId,
                pageSize = 20
            )
            if (res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    top20Coupon.value = res.body()!!.response
                }
            }
        }
    }

    fun getNewEvent() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = marketRepository.getLatestCoupon(
                memberId = member.value!!.studentId,
                pageSize = 20
            )
            if (res.isSuccessful) {
                withContext(Dispatchers.Main) {
                }
            }
        }
    }

    fun getMyCuration() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val res = marketRepository.getMyFavoriteMarkets(
//                memberId = member.value!!.studentId,
//                pageSize = 10,
//                lastModifiedAt = null
//            )
//            if (res.isSuccessful) {
//                withContext(Dispatchers.Main) {
//                    myCuration.value = res.body()!!.response.body
//                }
//            }
//        }
    }

    fun getDetailMarket(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = marketRepository.getMarketDetail(
                marketId = id
            )
            if(res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    detailMarket.value = res.body()!!.response
                }
            }
        }
    }

    fun featchMarket(page: Int, category: LargeCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = marketRepository.getMarkets(
                memberId = member.value!!.studentId,
                pageSize = 10,
                category = category.backendLable,
                lastPageIndex = page
            )
            if(res.isSuccessful) {
                allMarket.value = (allMarket.value ?: listOf<Market>())
            }
        }
    }
}