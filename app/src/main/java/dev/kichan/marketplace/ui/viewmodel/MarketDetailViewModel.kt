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

sealed class DownloadResult {
    data class Success(val couponName: String) : DownloadResult()
    data class Error(val message: String) : DownloadResult()
}

class MarketDetailViewModel(application: Application, private val marketId: Long) :
    AndroidViewModel(application) {

    private val marketsRepository = RepositoryProvider.provideMarketsRepository()
    private val couponsRepository = RepositoryProvider.provideCouponsRepository()
    private val paybackCouponsRepository = RepositoryProvider.providePaybackCouponsRepository()
    private val membersRepository = RepositoryProvider.provideMembersRepository()
    private val favoritesRepository = RepositoryProvider.provideFavoritesRepository()

    private val _uiState = MutableStateFlow(MarketDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<MarketDetailNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _downloadResult = MutableSharedFlow<DownloadResult>()
    val downloadResult = _downloadResult.asSharedFlow()

    init {
        loadMarketAndCoupons()
    }

    private fun loadMarketAndCoupons() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // 3개 API 병렬 호출
                val marketDetailsDeferred = async {
                    marketsRepository.getMarket_1(marketId)
                }
                val giftCouponsDeferred = async {
                    couponsRepository.getCouponList_4(marketId = marketId)
                }
                val paybackCouponsDeferred = async {
                    paybackCouponsRepository.getCouponList(
                        marketId = marketId,
                        couponId = null,
                        size = 100
                    )
                }

                // 모든 결과 대기
                val marketResponse = marketDetailsDeferred.await()
                val giftResponse = giftCouponsDeferred.await()
                val paybackResponse = paybackCouponsDeferred.await()

                // marketData 추출
                val marketData = if (marketResponse.isSuccessful) {
                    marketResponse.body()?.response
                } else {
                    null
                }

                // 쿠폰 변환
                val allCoupons = if (marketData != null) {
                    val giftCoupons = if (giftResponse.isSuccessful) {
                        giftResponse.body()?.response?.couponResDtos?.map {
                            it.toDisplayCoupon(marketData)
                        } ?: emptyList()
                    } else {
                        emptyList()
                    }

                    val paybackCoupons = if (paybackResponse.isSuccessful) {
                        paybackResponse.body()?.response?.couponResDtos?.map {
                            it.toDisplayCoupon(marketData)
                        } ?: emptyList()
                    } else {
                        emptyList()
                    }

                    giftCoupons + paybackCoupons
                } else {
                    emptyList()
                }

                // 한 번에 모든 상태 업데이트 (한 번의 recompose)
                _uiState.update {
                    it.copy(
                        marketData = marketData,
                        couponList = allCoupons,
                        isLoading = false
                    )
                }

                // 디버깅 로깅 (상태 업데이트 후)
                if (BuildConfig.DEBUG) {
                    val giftCount = giftResponse.body()?.response?.couponResDtos?.size ?: 0
                    val paybackCount = paybackResponse.body()?.response?.couponResDtos?.size ?: 0
                    Log.d("MarketDetail", "쿠폰 로딩 완료 - 일반: ${giftCount}개, 환급: ${paybackCount}개")

                    giftResponse.body()?.response?.couponResDtos?.forEach { coupon ->
                        Log.d(
                            "MarketDetail",
                            "일반 쿠폰 ${coupon.couponId}: hidden=${coupon.isHidden}, issued=${coupon.isMemberIssued}, available=${coupon.isAvailable}"
                        )
                    }
                    paybackResponse.body()?.response?.couponResDtos?.forEach { coupon ->
                        Log.d(
                            "MarketDetail",
                            "환급 쿠폰 ${coupon.couponId}: hidden=${coupon.isHidden}, issued=${coupon.isMemberIssued}"
                        )
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MarketDetail", "매장 및 쿠폰 로딩 실패", e)
                }
                _uiState.update {
                    it.copy(
                        marketData = null,
                        couponList = emptyList(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun checkFavoriteStatus() {
        viewModelScope.launch {
            try {
                val response = marketsRepository.getMemberFavoriteMarketList()
                if (response.isSuccessful) {
                    val favoriteMarkets =
                        response.body()?.response?.marketResDtos?.map { it.marketId } ?: emptyList()
                    _uiState.value =
                        _uiState.value.copy(isFavorite = favoriteMarkets.contains(marketId))
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MarketDetailViewModel", "즐겨찾기 상태 확인 실패", e)
                }
            }
        }
    }

    fun downloadGiftCoupon(couponId: Long) {
        viewModelScope.launch {
            try {
                val response = membersRepository.downloadGiftCoupon(couponId)
                if (response.isSuccessful) {
                    val coupon = _uiState.value.couponList.find { it.couponId == couponId }
                    _downloadResult.emit(DownloadResult.Success(coupon?.couponName ?: "쿠폰"))
                    _navigationEvent.emit(MarketDetailNavigationEvent.NavigateToMyPage)
                } else {
                    when (response.code()) {
                        409 -> _downloadResult.emit(DownloadResult.Error("이미 발급받은 쿠폰입니다"))
                        else -> _downloadResult.emit(DownloadResult.Error("쿠폰 발급 실패"))
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MarketDetailViewModel", "일반 쿠폰 다운로드 실패: couponId=$couponId", e)
                }
                _downloadResult.emit(DownloadResult.Error(e.message ?: "네트워크 오류"))
            }
        }
    }

    fun downloadPaybackCoupon(couponId: Long) {
        viewModelScope.launch {
            try {
                val response = membersRepository.downloadPaybackCoupon(couponId)
                if (response.isSuccessful) {
                    val coupon = _uiState.value.couponList.find { it.couponId == couponId }
                    _downloadResult.emit(DownloadResult.Success(coupon?.couponName ?: "쿠폰"))
                    _navigationEvent.emit(MarketDetailNavigationEvent.NavigateToMyPage)
                } else {
                    when (response.code()) {
                        409 -> _downloadResult.emit(DownloadResult.Error("이미 발급받은 쿠폰입니다"))
                        else -> _downloadResult.emit(DownloadResult.Error("쿠폰 발급 실패"))
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MarketDetailViewModel", "환급 쿠폰 다운로드 실패: couponId=$couponId", e)
                }
                _downloadResult.emit(DownloadResult.Error(e.message ?: "네트워크 오류"))
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
