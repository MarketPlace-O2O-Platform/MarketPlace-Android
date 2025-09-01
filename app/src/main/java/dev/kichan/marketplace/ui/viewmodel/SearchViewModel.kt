package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MarketRes
import dev.kichan.marketplace.model.dto.TopPopularCouponRes
import dev.kichan.marketplace.model.repository.RecentKeywordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PopularCoupon(
    val couponId: Long,
    val couponName: String,
    val marketId: Long,
    val marketName: String,
    val thumbnail: String,
    val issuedCount: Long,
    val isFavorite: Boolean
)

data class SearchUiState(
    val searchKey: String = "",
    val recentKeywords: List<String> = emptyList(),
    val searchResults: List<MarketRes> = emptyList(),
    val popularCoupons: List<PopularCoupon> = emptyList(),
    val isLoading: Boolean = false,
    val isFirstSearch: Boolean = true,
)

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val marketsRepository = RepositoryProvider.provideMarketsRepository()
    private val couponsRepository = RepositoryProvider.provideCouponsRepository()
    private val favoritesRepository = RepositoryProvider.provideFavoritesRepository()
    private val recentKeywordRepository = RecentKeywordRepository(application)

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getRecentKeywords()
    }

    fun onSearchKeyChanged(key: String) {
        _uiState.value = _uiState.value.copy(searchKey = key)
        if (key.length >= 2) {
            searchMarkets(key)
        } else {
            _uiState.value = _uiState.value.copy(searchResults = emptyList(), isFirstSearch = true)
        }
    }

    fun search() {
        val keyword = _uiState.value.searchKey
        if (keyword.isNotBlank()) {
            addRecentKeyword(keyword)
            searchMarkets(keyword)
        }
    }

    private fun getRecentKeywords() {
        _uiState.value = _uiState.value.copy(recentKeywords = recentKeywordRepository.getRecentKeywords())
    }

    private fun addRecentKeyword(keyword: String) {
        recentKeywordRepository.addRecentKeyword(keyword)
        getRecentKeywords()
    }

    fun getPopularCoupons() {
        viewModelScope.launch {
            try {
                val popularCouponsResponse = couponsRepository.getPopularCoupon()
                val favoriteMarketsResponse = marketsRepository.getMemberFavoriteMarketList()

                if (popularCouponsResponse.isSuccessful && favoriteMarketsResponse.isSuccessful) {
                    val popularCoupons = popularCouponsResponse.body()?.response ?: emptyList()
                    val favoriteMarkets = favoriteMarketsResponse.body()?.response?.marketResDtos?.map { it.marketId }?.toSet() ?: emptySet()

                    val coupons = popularCoupons.map { coupon ->
                        PopularCoupon(
                            couponId = coupon.couponId,
                            couponName = coupon.couponName,
                            marketId = coupon.marketId,
                            marketName = coupon.marketName,
                            thumbnail = coupon.thumbnail,
                            issuedCount = coupon.issuedCount,
                            isFavorite = favoriteMarkets.contains(coupon.marketId)
                        )
                    }
                    _uiState.value = _uiState.value.copy(popularCoupons = coupons)
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
                val updatedCoupons = _uiState.value.popularCoupons.map {
                    if (it.marketId == marketId) it.copy(isFavorite = true) else it
                }
                _uiState.value = _uiState.value.copy(popularCoupons = updatedCoupons)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun unfavorite(marketId: Long) {
        viewModelScope.launch {
            try {
                favoritesRepository.unfavorite(marketId)
                val updatedCoupons = _uiState.value.popularCoupons.map {
                    if (it.marketId == marketId) it.copy(isFavorite = false) else it
                }
                _uiState.value = _uiState.value.copy(popularCoupons = updatedCoupons)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun searchMarkets(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = marketsRepository.searchMarket_1(name = query)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        searchResults = response.body()?.response?.marketResDtos ?: emptyList(),
                        isFirstSearch = false
                    )
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
