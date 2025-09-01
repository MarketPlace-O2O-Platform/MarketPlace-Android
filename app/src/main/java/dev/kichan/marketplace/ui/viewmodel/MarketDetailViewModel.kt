package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.CouponRes
import dev.kichan.marketplace.model.dto.MarketDetailsRes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MarketDetailUiState(
    val marketData: MarketDetailsRes? = null,
    val couponList: List<CouponRes> = emptyList(),
    val isLoading: Boolean = false,
)

sealed class MarketDetailNavigationEvent {
    object NavigateToMyPage : MarketDetailNavigationEvent()
}

class MarketDetailViewModel(application: Application, private val marketId: Long) : AndroidViewModel(application) {

    private val marketsRepository = RepositoryProvider.provideMarketsRepository()
    private val couponsRepository = RepositoryProvider.provideCouponsRepository()
    private val membersRepository = RepositoryProvider.provideMembersRepository()
    private val favoritesRepository = RepositoryProvider.provideFavoritesRepository()

    private val _uiState = MutableStateFlow(MarketDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<MarketDetailNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        getMarketDetails()
        getMarketCoupons()
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
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = couponsRepository.getCouponList_4(marketId = marketId)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(couponList = response.body()?.response?.couponResDtos ?: emptyList())
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
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
                favoritesRepository.favorite(marketId)
                // Refresh the market details to get the updated favorite status
                getMarketDetails()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
