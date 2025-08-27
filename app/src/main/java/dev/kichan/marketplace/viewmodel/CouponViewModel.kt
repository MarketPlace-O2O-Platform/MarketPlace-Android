package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.CouponRes
import dev.kichan.marketplace.model.repository.CouponsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CouponListUiState(
    val couponList: List<CouponRes> = emptyList(),
    val isLoading: Boolean = false,
)

class CouponViewModel() : ViewModel() {
    private val couponsRepository: CouponsRepository = RepositoryProvider.provideCouponsRepository();
    private val _couponListUiState = MutableStateFlow(CouponListUiState())
    val couponListUiState = _couponListUiState.asStateFlow()

    fun getCouponList(type: String) {
//        viewModelScope.launch {
//            _couponListUiState.update { it.copy(isLoading = true) }
//            val response : CouponRes[] = when (type) {
//                "popular" -> {
//                    val res = couponsRepository.getPopularCoupon()
//                    res.body()?.response
//                }
//                "latest" -> {
//                    couponsRepository.getLatestCoupon()
//                }
//                else -> null
//            }
//            _couponListUiState.update { it.copy(isLoading = false) }
//        }
    }

    fun downloadCoupon(id: Long) {
        viewModelScope.launch {
            // TODO: Implement download coupon logic
        }
    }
}