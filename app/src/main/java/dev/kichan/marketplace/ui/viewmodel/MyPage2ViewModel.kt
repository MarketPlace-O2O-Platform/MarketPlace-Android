package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.IssuedCouponRes
import dev.kichan.marketplace.model.dto.MemberRes
import dev.kichan.marketplace.model.dto.PaybackRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class EndedCoupon {
    data class EndedPayback(val coupon: IssuedCouponRes) : EndedCoupon()
    data class EndedGift(val coupon: IssuedCouponRes) : EndedCoupon()
}

data class MyPage2UiState(
    val member: MemberRes? = null,
    val paybackCouponList: List<IssuedCouponRes> = emptyList(),
    val giftCouponList: List<IssuedCouponRes> = emptyList(),
    val endedCouponList: List<EndedCoupon> = emptyList(),
    val isLoading: Boolean = false,
)

class MyPage2ViewModel() : ViewModel() {
    private val membersRepository = RepositoryProvider.provideMembersRepository()

    private val _uiState = MutableStateFlow(MyPage2UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMemberInfo()
        getPaybackCoupons()
        getGiftCoupons()
        getEndedCoupons()
    }

    fun getMemberInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = membersRepository.getMember()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(member = response.body()?.response)
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun getPaybackCoupons() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = membersRepository.getPaybackCoupon(type = "ISSUED")
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(paybackCouponList = response.body()?.response?.couponResDtos ?: emptyList())
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun getGiftCoupons() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = membersRepository.getCoupons(type = "ISSUED")
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(giftCouponList = response.body()?.response?.couponResDtos ?: emptyList())
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun getEndedCoupons() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
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
                _uiState.value = _uiState.value.copy(endedCouponList = endedCoupons)

            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun useGiftCoupon(memberCouponId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                membersRepository.useCoupon(memberCouponId)
                _uiState.value = _uiState.value.copy(giftCouponList = _uiState.value.giftCouponList.filter { it.memberCouponId != memberCouponId })
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}