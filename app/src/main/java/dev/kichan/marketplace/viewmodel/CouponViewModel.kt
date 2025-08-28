package dev.kichan.marketplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.repository.CouponsRepository
import dev.kichan.marketplace.ui.component.atoms.CouponListItemProps
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CouponListUiState(
    val couponList: List<CouponListItemProps> = emptyList(),
    val isLoading: Boolean = false,
    val hasNext: Boolean = true,
    val lastIssuedCount: Long? = null,
    val lastCreatedAt: String? = null,
    val lastCouponId: Long? = null,
)

class CouponViewModel() : ViewModel() {
    private val couponsRepository: CouponsRepository = RepositoryProvider.provideCouponsRepository();
    private val _couponListUiState = MutableStateFlow(CouponListUiState())
    val couponListUiState = _couponListUiState.asStateFlow()

    fun getCouponList(type: String) {
        _couponListUiState.value = CouponListUiState()
        loadMoreCoupons(type)
    }

    fun loadMoreCoupons(type: String) {
        if (!_couponListUiState.value.hasNext || _couponListUiState.value.isLoading) return

        viewModelScope.launch {
            _couponListUiState.update { it.copy(isLoading = true) }

            val state = _couponListUiState.value
            val res = when (type) {
                "popular" -> couponsRepository.getPopularCouponAll(
                    lastIssuedCount = state.lastIssuedCount,
                    lastCouponId = state.lastCouponId,
                    pageSize = 10
                )
                "latest" -> couponsRepository.getLatestCouponAll(
                    lastCreatedAt = state.lastCreatedAt,
                    lastCouponId = state.lastCouponId,
                    pageSize = 10
                )
                else -> throw IllegalArgumentException("Invalid coupon type")
            }

            if (res.isSuccessful) {
                val response = res.body()?.response
                response?.let {
                    val newCoupons = it.couponResDtos.map {
                        CouponListItemProps(
                            id = it.couponId,
                            name = it.couponName,
                            marketName = it.marketName,
                            marketId = it.marketId,
                            imageUrl = NetworkModule.getImage(it.thumbnail),
                            isMemberIssued = it.isMemberIssued,
                            address = it.address,
                            isAvailable = it.isAvailable,
                        )
                    }
                    _couponListUiState.update { currentState ->
                        currentState.copy(
                            couponList = currentState.couponList + newCoupons,
                            isLoading = false,
                            hasNext = it.hasNext,
                            lastIssuedCount = it.couponResDtos.lastOrNull()?.issuedCount,
                            lastCreatedAt = it.couponResDtos.lastOrNull()?.couponCreatedAt,
                            lastCouponId = it.couponResDtos.lastOrNull()?.couponId
                        )
                    }
                }
            } else {
                _couponListUiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun downloadCoupon(id: Long) {
        viewModelScope.launch {

        }
    }
}