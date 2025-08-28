package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MarketRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MarketListUiState(
    val markets: List<MarketRes> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: LargeCategory,
    val lastPageIndex: Long? = null,
    val hasNext: Boolean = true,
)

class MarketListViewModel(application: Application, initialCategory: LargeCategory) : AndroidViewModel(application) {

    private val marketsRepository = RepositoryProvider.provideMarketsRepository()
    private val favoritesRepository = RepositoryProvider.provideFavoritesRepository()

    private val _uiState = MutableStateFlow(MarketListUiState(selectedCategory = initialCategory))
    val uiState = _uiState.asStateFlow()

    init {
        getMarkets(true)
    }

    fun getMarkets(isNewCategory: Boolean = false) {
        if (_uiState.value.isLoading || !_uiState.value.hasNext) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val lastPageIndex = if (isNewCategory) null else _uiState.value.lastPageIndex
                val response = marketsRepository.getMarket(
                    lastPageIndex = lastPageIndex,
                    category = _uiState.value.selectedCategory.backendLabel
                )
                if (response.isSuccessful) {
                    val marketPage = response.body()?.response
                    if (marketPage != null) {
                        val currentMarkets = if (isNewCategory) emptyList() else _uiState.value.markets
                        _uiState.value = _uiState.value.copy(
                            markets = currentMarkets + marketPage.marketResDtos,
                            hasNext = marketPage.hasNext,
                            lastPageIndex = marketPage.marketResDtos.lastOrNull()?.marketId
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
                // Refresh the list after favoriting
                getMarkets(true)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun onCategoryChanged(category: LargeCategory) {
        _uiState.value = MarketListUiState(
            selectedCategory = category,
            markets = emptyList(),
            isLoading = false,
            lastPageIndex = null,
            hasNext = true
        )
        getMarkets(true)
    }
}
