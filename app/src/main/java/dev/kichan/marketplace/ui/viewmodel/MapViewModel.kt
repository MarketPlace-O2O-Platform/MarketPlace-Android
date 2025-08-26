package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MarketRes
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MarketWithCoords(
    val market: MarketRes,
    val coords: LatLng
)

data class MapUiState(
    val markets: List<MarketWithCoords> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: LargeCategory = LargeCategory.All,
)

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val marketsRepository = RepositoryProvider.provideMarketsRepository()
    private val favoritesRepository = RepositoryProvider.provideFavoritesRepository()
    private val kakaoRepository = RepositoryProvider.provideKakaoRepository()

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    fun getMarkets() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val allMarkets = mutableListOf<MarketRes>()
                var page = 0
                var isLastPage = false
                while (!isLastPage) {
                    val response = marketsRepository.getMarket(lastPageIndex = page.toLong(), category = _uiState.value.selectedCategory.name, pageSize = 20)
                    if (response.isSuccessful) {
                        val marketPage = response.body()?.response
                        if (marketPage != null) {
                            allMarkets.addAll(marketPage.marketResDtos)
                            isLastPage = marketPage.hasNext
                        } else {
                            isLastPage = true
                        }
                    } else {
                        isLastPage = true
                    }
                    page++
                }

                val marketsWithCoords = allMarkets.map { market ->
                    async {
                        val addressResponse = kakaoRepository.getAddress(market.address)
                        if (addressResponse.isSuccessful) {
                            val document = addressResponse.body()?.documents?.firstOrNull()
                            if (document != null) {
                                val lat = document.y.toDouble()
                                val lng = document.x.toDouble()
                                MarketWithCoords(market, LatLng(lat, lng))
                            } else {
                                null
                            }
                        } else {
                            null
                        }
                    }
                }.awaitAll().filterNotNull()

                _uiState.value = _uiState.value.copy(markets = marketsWithCoords)

            } catch (e: Exception) {
                // Handle error
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun favorite(marketId: Long) {
        viewModelScope.launch {
            try {
                favoritesRepository.createCoupon_1(marketId)
                // Refresh the list after favoriting
                getMarkets()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun onCategoryChanged(category: LargeCategory) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
        getMarkets()
    }
}
