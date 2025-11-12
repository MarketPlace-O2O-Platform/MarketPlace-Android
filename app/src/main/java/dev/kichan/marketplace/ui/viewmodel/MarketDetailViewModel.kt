package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.DisplayCoupon
import dev.kichan.marketplace.model.dto.MarketDetailsRes
import dev.kichan.marketplace.model.dto.toDisplayCoupon
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MarketDetailUiState(
    val marketData: MarketDetailsRes? = null,
    val couponList: List<DisplayCoupon> = emptyList(),
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
)

sealed class MarketDetailNavigationEvent {
    object NavigateToMyPage : MarketDetailNavigationEvent()
}

class MarketDetailViewModel(application: Application, private val marketId: Long) : AndroidViewModel(application) {

    private val marketsRepository = RepositoryProvider.provideMarketsRepository()
    private val couponsRepository = RepositoryProvider.provideCouponsRepository()
    private val paybackCouponsRepository = RepositoryProvider.providePaybackCouponsRepository()
    private val membersRepository = RepositoryProvider.provideMembersRepository()
    private val favoritesRepository = RepositoryProvider.provideFavoritesRepository()

    private val _uiState = MutableStateFlow(MarketDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<MarketDetailNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        getMarketDetails()
        getMarketCoupons()
        checkFavoriteStatus()
    }

    fun checkFavoriteStatus() {
        viewModelScope.launch {
            try {
                val response = marketsRepository.getMemberFavoriteMarketList()
                if (response.isSuccessful) {
                    val favoriteMarkets = response.body()?.response?.marketResDtos?.map { it.marketId } ?: emptyList()
                    _uiState.value = _uiState.value.copy(isFavorite = favoriteMarkets.contains(marketId))
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MarketDetailViewModel", "즐겨찾기 상태 확인 실패", e)
                }
            }
        }
    }

    private fun getMarketDetails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = marketsRepository.getMarket_1(marketId)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(marketData = response.body()?.response)
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun getMarketCoupons() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // 병렬 API 호출
                val giftCouponsDeferred = async {
                    couponsRepository.getCouponList_4(marketId = marketId)
                }
                val paybackCouponsDeferred = async {
                    paybackCouponsRepository.getCouponList(marketId = marketId, couponId = null, size = 100)
                }

                // 결과 대기
                val giftResponse = giftCouponsDeferred.await()
                val paybackResponse = paybackCouponsDeferred.await()

                // 디버깅 로깅 (디버그 빌드에서만)
                if (BuildConfig.DEBUG) {
                    val giftCount = giftResponse.body()?.response?.couponResDtos?.size ?: 0
                    val paybackCount = paybackResponse.body()?.response?.couponResDtos?.size ?: 0
                    Log.d("MarketDetail", "쿠폰 로딩 완료 - 일반: ${giftCount}개, 환급: ${paybackCount}개")

                    // 각 쿠폰 상태 로깅
                    giftResponse.body()?.response?.couponResDtos?.forEach { coupon ->
                        Log.d("MarketDetail", "일반 쿠폰 ${coupon.couponId}: hidden=${coupon.isHidden}, issued=${coupon.isMemberIssued}, available=${coupon.isAvailable}")
                    }
                    paybackResponse.body()?.response?.couponResDtos?.forEach { coupon ->
                        Log.d("MarketDetail", "환급 쿠폰 ${coupon.couponId}: hidden=${coupon.isHidden}, issued=${coupon.isMemberIssued}")
                    }
                }

                // DisplayCoupon으로 변환
                val giftCoupons = if (giftResponse.isSuccessful) {
                    giftResponse.body()?.response?.couponResDtos?.map {
                        it.toDisplayCoupon()
                    } ?: emptyList()
                } else {
                    emptyList()
                }

                val paybackCoupons = if (paybackResponse.isSuccessful) {
                    val marketData = _uiState.value.marketData
                    if (marketData != null) {
                        paybackResponse.body()?.response?.couponResDtos?.map {
                            it.toDisplayCoupon(marketData)
                        } ?: emptyList()
                    } else {
                        emptyList()
                    }
                } else {
                    emptyList()
                }

                // 병합: GIFT 먼저, PAYBACK 나중 (자동 순서 보장)
                val allCoupons = giftCoupons + paybackCoupons

                _uiState.update {
                    it.copy(
                        couponList = allCoupons,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                Log.e("MarketDetail", "쿠폰 로딩 실패", e)
                _uiState.update {
                    it.copy(
                        couponList = emptyList(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun downloadGiftCoupon(couponId: Long) {
        viewModelScope.launch {
            try {
                val response = membersRepository.downloadGiftCoupon(couponId)
                if (response.isSuccessful) {
                    _navigationEvent.emit(MarketDetailNavigationEvent.NavigateToMyPage)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun downloadPaybackCoupon(couponId: Long) {
        viewModelScope.launch {
            try {
                val response = membersRepository.downloadPaybackCoupon(couponId)
                if (response.isSuccessful) {
                    _navigationEvent.emit(MarketDetailNavigationEvent.NavigateToMyPage)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun favorite(marketId: Long) {
        viewModelScope.launch {
            try {
                // 1. 현재 북마크 상태 확인 (UI 토글용)
                val isFavorite = _uiState.value.isFavorite

                // 2. 서버 토글 API 호출 (POST만 사용)
                val response = favoritesRepository.favorite(marketId)

                // 3. 성공 시에만 UI 토글
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(isFavorite = !isFavorite)
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MarketDetailViewModel", "북마크 토글 실패: marketId=$marketId", e)
                }
            }
        }
    }
}
