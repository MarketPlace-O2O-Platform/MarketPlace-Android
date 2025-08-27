package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.TopClosingCouponRes
import dev.kichan.marketplace.model.dto.TopLatestCouponRes
import dev.kichan.marketplace.model.dto.TopPopularCouponRes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val popularCoupons: List<TopPopularCouponRes> = emptyList(),
    val latestCoupons: List<TopLatestCouponRes> = emptyList(),
    val closingCoupons: List<TopClosingCouponRes> = emptyList(),
    val isPopularLoading: Boolean = false,
    val isLatestLoading: Boolean = false,
    val isClosingLoading: Boolean = false,
)

sealed class HomeNavigationEvent {
    object NavigateToSearch : HomeNavigationEvent()
    data class NavigateToEventDetail(val marketId: Long) : HomeNavigationEvent()
    data class NavigateToCouponListPage(val type: String) : HomeNavigationEvent()
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val couponsRepository = RepositoryProvider.provideCouponsRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<HomeNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun getPopularCoupons() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isPopularLoading = true)
            try {
                val response = couponsRepository.getPopularCoupon()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(popularCoupons = response.body()?.response ?: emptyList())
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isPopularLoading = false)
            }
        }
    }

    fun getLatestCoupons() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLatestLoading = true)
            try {
                val response = couponsRepository.getLatestCoupon()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(latestCoupons = response.body()?.response ?: emptyList())
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLatestLoading = false)
            }
        }
    }

    fun getClosingCoupons() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isClosingLoading = true)
            try {
                val response = couponsRepository.getClosingCoupon()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(closingCoupons = response.body()?.response ?: emptyList())
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isClosingLoading = false)
            }
        }
    }

    fun onSearchClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToSearch)
        }
    }

    fun onEventDetailClicked(marketId: Long) {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToEventDetail(marketId))
        }
    }

    fun onCouponListPageClicked(type: String) {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToCouponListPage(type))
        }
    }
}
