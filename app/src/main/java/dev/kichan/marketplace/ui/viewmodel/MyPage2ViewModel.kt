package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.IssuedCouponRes
import dev.kichan.marketplace.model.dto.MemberRes
import dev.kichan.marketplace.model.dto.PaybackRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MyPage2UiState(
    val member: MemberRes? = null,
    val paybackCouponList: List<PaybackRes> = emptyList(),
    val giftCouponList: List<IssuedCouponRes> = emptyList(),
    val isLoading: Boolean = false,
)

class MyPage2ViewModel(application: Application) : AndroidViewModel(application) {
    private val membersRepository = RepositoryProvider.provideMembersRepository()
    private val paybackCouponsRepository = RepositoryProvider.providePaybackCouponsRepository()

    private val _uiState = MutableStateFlow(MyPage2UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMemberInfo()
        getPaybackCoupons()
        getGiftCoupons()
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
                val response = membersRepository.getPaybackCoupon(type = "payback")
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
                val response = membersRepository.getCoupons(type = "gift")
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
}
