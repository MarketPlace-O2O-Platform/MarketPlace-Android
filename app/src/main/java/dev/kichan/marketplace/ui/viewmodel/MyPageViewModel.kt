package dev.kichan.marketplace.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.common.AnalyticsManager
import dev.kichan.marketplace.model.TokenManager
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.IssuedCouponRes
import dev.kichan.marketplace.model.dto.MemberRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class EndedCoupon {
    data class EndedPayback(val coupon: IssuedCouponRes) : EndedCoupon()
    data class EndedGift(val coupon: IssuedCouponRes) : EndedCoupon()
}

data class MyPageUiState(
    val member: MemberRes? = null,
    val paybackCouponList: List<IssuedCouponRes> = emptyList(),
    val giftCouponList: List<IssuedCouponRes> = emptyList(),
    val endedCouponList: List<EndedCoupon> = emptyList(),
    val isLoading: Boolean = false,
    val isCouponsLoaded: Boolean = false,
)

class MyPageViewModel() : ViewModel() {
    private val membersRepository = RepositoryProvider.provideMembersRepository()

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState = _uiState.asStateFlow()

    private var loadingCount = 0

    private fun startLoading() {
        loadingCount++
        if (loadingCount == 1) _uiState.value = _uiState.value.copy(isLoading = true)
    }

    private fun stopLoading() {
        loadingCount = (loadingCount - 1).coerceAtLeast(0)
        if (loadingCount == 0) _uiState.value = _uiState.value.copy(isLoading = false)
    }

    init {
        getMemberInfo()
        getPaybackCoupons()
        getGiftCoupons()
        getEndedCoupons()
    }

    fun getMemberInfo() {
        viewModelScope.launch {
            startLoading()
            try {
                val response = membersRepository.getMember()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(member = response.body()?.response)
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                stopLoading()
            }
        }
    }

    fun getPaybackCoupons() {
        viewModelScope.launch {
            startLoading()
            try {
                val response = membersRepository.getPaybackCoupon(type = "ISSUED")
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        paybackCouponList = response.body()?.response?.couponResDtos ?: emptyList(),
                        isCouponsLoaded = true
                    )
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                stopLoading()
            }
        }
    }

    fun getGiftCoupons() {
        viewModelScope.launch {
            startLoading()
            try {
                val response = membersRepository.getCoupons(type = "ISSUED")
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        giftCouponList = response.body()?.response?.couponResDtos ?: emptyList(),
                        isCouponsLoaded = true
                    )
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                stopLoading()
            }
        }
    }

    fun getEndedCoupons() {
        viewModelScope.launch {
            startLoading()
            try {
                val endedPaybackResponse = membersRepository.getPaybackCoupon(type = "ENDED")
                val endedGiftResponse = membersRepository.getCoupons(type = "ENDED")

                val endedCoupons = mutableListOf<EndedCoupon>()

                if (endedPaybackResponse.isSuccessful) {
                    endedPaybackResponse.body()?.response?.couponResDtos?.forEach {
                        endedCoupons.add(EndedCoupon.EndedPayback(it))
                    }
                }

                if (endedGiftResponse.isSuccessful) {
                    endedGiftResponse.body()?.response?.couponResDtos?.forEach {
                        endedCoupons.add(EndedCoupon.EndedGift(it))
                    }
                }
                _uiState.value = _uiState.value.copy(
                    endedCouponList = endedCoupons,
                    isCouponsLoaded = true
                )

            } catch (e: Exception) {
                // Handle error
            } finally {
                stopLoading()
            }
        }
    }

    fun useGiftCoupon(memberCouponId: Long) {
        viewModelScope.launch {
            startLoading()
            try {
                val usedCoupon = _uiState.value.giftCouponList.find { it.memberCouponId == memberCouponId }
                membersRepository.useCoupon(memberCouponId)

                // 사용한 쿠폰을 증정형 목록에서 제거하고 끝난 쿠폰 목록에 추가
                if (usedCoupon != null) {
                    AnalyticsManager.logCouponUse(
                        couponId = usedCoupon.couponId,
                        couponType = usedCoupon.couponType
                    )
                    _uiState.value = _uiState.value.copy(
                        giftCouponList = _uiState.value.giftCouponList.filter { it.memberCouponId != memberCouponId },
                        endedCouponList = _uiState.value.endedCouponList + EndedCoupon.EndedGift(usedCoupon)
                    )
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                stopLoading()
            }
        }
    }

    fun refreshCoupons() {
        getPaybackCoupons()
        getGiftCoupons()
        getEndedCoupons()  // 끝난 쿠폰도 갱신
    }

    fun logout(context : Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            TokenManager.clearToken()
            onSuccess()
        }
    }
}
