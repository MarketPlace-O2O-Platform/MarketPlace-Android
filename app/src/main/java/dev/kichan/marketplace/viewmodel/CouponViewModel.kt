package dev.kichan.marketplace.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.repository.CouponsRepository
import dev.kichan.marketplace.ui.component.atoms.CouponListItemProps
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CouponListUiState(
    val couponList: List<CouponListItemProps> = emptyList(),
    val isLoading: Boolean = false,
    val hasNext: Boolean = true,
    val lastIssuedCount: Long? = null,
    val lastOrderNo: Long? = null,
    val lastCreatedAt: String? = null,
    val lastCouponId: Long? = null,
)

sealed class DownloadResult {
    data class Success(val couponName: String) : DownloadResult()
    data class Error(val message: String) : DownloadResult()
}

class CouponViewModel() : ViewModel() {
    private val couponsRepository: CouponsRepository = RepositoryProvider.provideCouponsRepository();
    private val mamberRepository = RepositoryProvider   .provideMembersRepository();
    private val _couponListUiState = MutableStateFlow(CouponListUiState())
    val couponListUiState = _couponListUiState.asStateFlow()

    private val _downloadResult = MutableSharedFlow<DownloadResult>()
    val downloadResult = _downloadResult.asSharedFlow()

    fun getCouponList(type: String) {
        _couponListUiState.value = CouponListUiState()
        loadMoreCoupons(type)
    }

    fun loadMoreCoupons(type: String) {
        if (!_couponListUiState.value.hasNext || _couponListUiState.value.isLoading) return

        viewModelScope.launch {
            try {
                _couponListUiState.update { it.copy(isLoading = true) }

                val state = _couponListUiState.value
                val res = when (type) {
                    "popular" -> {
                        // 마지막 쿠폰의 couponType에 따라 다른 값 전달
                        val lastCoupon = state.couponList.lastOrNull()
                        val (lastIssuedCountValue, couponTypeValue) = when (lastCoupon?.couponType) {
                            "GIFT" -> Pair(state.lastIssuedCount, "GIFT")
                            "PAYBACK" -> Pair(state.lastOrderNo, "PAYBACK")
                            else -> Pair(null, null)  // 첫 페이지
                        }
                        couponsRepository.getPopularCouponAll(
                            lastIssuedCount = lastIssuedCountValue,
                            lastCouponId = state.lastCouponId,
                            pageSize = 10,
                            couponType = couponTypeValue
                        )
                    }
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
                        Log.d("test", it.toString())
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
                                couponType = it.couponType
                            )
                        }
                        _couponListUiState.update { currentState ->
                            currentState.copy(
                                couponList = currentState.couponList + newCoupons,
                                isLoading = false,
                                hasNext = it.hasNext,
                                lastIssuedCount = it.couponResDtos.lastOrNull()?.issuedCount,
                                lastOrderNo = it.couponResDtos.lastOrNull()?.orderNo,
                                lastCreatedAt = it.couponResDtos.lastOrNull()?.couponCreatedAt,
                                lastCouponId = it.couponResDtos.lastOrNull()?.couponId
                            )
                        }
                    }
                } else {
                    _couponListUiState.update { it.copy(isLoading = false) }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("CouponViewModel", "쿠폰 목록 로드 실패: type=$type", e)
                }
                _couponListUiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun downloadCoupon(id: Long) {
        val coupon = _couponListUiState.value.couponList.find { it.id == id }
        if(coupon == null) {
            viewModelScope.launch {
                _downloadResult.emit(DownloadResult.Error("쿠폰 정보를 찾을 수 없습니다"))
            }
            return
        }

        viewModelScope.launch {
            try {
                val response = if(coupon.couponType == "PAYBACK") {
                    mamberRepository.downloadPaybackCoupon(id)
                } else {
                    mamberRepository.downloadGiftCoupon(id)
                }

                if (response.isSuccessful) {
                    _couponListUiState.update { state ->
                        state.copy(
                            couponList = state.couponList.map { c ->
                                if(c.id == id) c.copy(isMemberIssued = true) else c
                            }
                        )
                    }
                    _downloadResult.emit(DownloadResult.Success(coupon.name))
                } else {
                    _downloadResult.emit(DownloadResult.Error("쿠폰 발급 실패"))
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("CouponViewModel", "쿠폰 다운로드 실패: id=$id", e)
                }
                _downloadResult.emit(DownloadResult.Error(e.message ?: "네트워크 오류"))
            }
        }
    }
}