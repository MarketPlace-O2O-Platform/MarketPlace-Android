package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MarketRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CurationPageUiState(
    val markets: List<MarketRes> = emptyList(),
    val isLoading: Boolean = false,
    val lastModifiedAt: String? = null,
    val hasNext: Boolean = true,
)

class CurationPageViewModel(application: Application) : AndroidViewModel(application) {

    private val marketsRepository = RepositoryProvider.provideMarketsRepository()
    private val favoritesRepository = RepositoryProvider.provideFavoritesRepository()

    private val _uiState = MutableStateFlow(CurationPageUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getFavoriteMarkets(true)
    }

    fun getFavoriteMarkets(isRefresh: Boolean = false) {
        if (_uiState.value.isLoading || !_uiState.value.hasNext) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val lastModifiedAt = if (isRefresh) null else _uiState.value.lastModifiedAt
                val response = marketsRepository.getMemberFavoriteMarketList(
                    lastModifiedAt = lastModifiedAt,
                )
                if (response.isSuccessful) {
                    val marketPage = response.body()?.response
                    if (marketPage != null) {
                        val currentMarkets = if (isRefresh) emptyList() else _uiState.value.markets
                        _uiState.value = _uiState.value.copy(
                            markets = currentMarkets + marketPage.marketResDtos,
                            hasNext = marketPage.hasNext,
                            lastModifiedAt = marketPage.marketResDtos.lastOrNull()?.favoriteModifiedAt
                        )
                    }
                }
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
                favoritesRepository.favorite(marketId)
                getFavoriteMarkets(true)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun unfavorite(marketId: Long) {
        viewModelScope.launch {
            try {
                favoritesRepository.unfavorite(marketId)
                getFavoriteMarkets(true)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
