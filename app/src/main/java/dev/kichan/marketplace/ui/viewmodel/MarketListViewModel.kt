package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.BuildConfig
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
        if (_uiState.value.isLoading || !_uiState.value.hasNext) {
            if (BuildConfig.DEBUG) {
                Log.d("MarketListViewModel", "getMarkets 차단됨 - isLoading: ${_uiState.value.isLoading}, hasNext: ${_uiState.value.hasNext}")
            }
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val lastPageIndex = if (isNewCategory) null else _uiState.value.lastPageIndex
                if (BuildConfig.DEBUG) {
                    Log.d("MarketListViewModel", "API 호출 - lastPageIndex: $lastPageIndex, category: ${_uiState.value.selectedCategory.backendLabel}, isNewCategory: $isNewCategory")
                }
                val response = marketsRepository.getMarket(
                    lastPageIndex = lastPageIndex,
                    category = _uiState.value.selectedCategory.backendLabel,
                    pageSize = 60  // 임시: 백엔드 API 버그로 인해 큰 값 사용
                )
                if (response.isSuccessful) {
                    val marketPage = response.body()?.response
                    if (marketPage != null) {
                        if (BuildConfig.DEBUG) {
                            Log.d("MarketListViewModel", "API 응답 성공 - 받은 매장 수: ${marketPage.marketResDtos.size}, hasNext: ${marketPage.hasNext}")
                            Log.d("MarketListViewModel", "첫 매장 ID: ${marketPage.marketResDtos.firstOrNull()?.marketId}, 마지막 매장 ID: ${marketPage.marketResDtos.lastOrNull()?.marketId}")
                        }
                        val currentMarkets = if (isNewCategory) emptyList() else _uiState.value.markets
                        _uiState.value = _uiState.value.copy(
                            markets = currentMarkets + marketPage.marketResDtos,
                            hasNext = marketPage.hasNext,
                            lastPageIndex = marketPage.marketResDtos.lastOrNull()?.marketId
                        )
                        if (BuildConfig.DEBUG) {
                            Log.d("MarketListViewModel", "상태 업데이트 완료 - 전체 매장 수: ${_uiState.value.markets.size}, 새 lastPageIndex: ${_uiState.value.lastPageIndex}")
                        }
                    }
                } else {
                    if (BuildConfig.DEBUG) {
                        Log.e("MarketListViewModel", "API 응답 실패 - code: ${response.code()}, message: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MarketListViewModel", "API 호출 예외 발생", e)
                }
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun favorite(marketId: Long) {
        viewModelScope.launch {
            try {
                // 1. 현재 북마크 상태 확인 (UI 토글용)
                val currentMarket = _uiState.value.markets.find { it.marketId == marketId }
                val isFavorite = currentMarket?.isFavorite ?: false

                // 2. 서버 토글 API 호출 (POST만 사용)
                val response = favoritesRepository.favorite(marketId)

                // 3. 성공 시에만 UI 토글
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        markets = _uiState.value.markets.map {
                            if (it.marketId == marketId)
                                it.copy(isFavorite = !isFavorite)
                            else
                                it
                        }
                    )
                }
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

    fun refreshBookmarkStates() {
        viewModelScope.launch {
            try {
                val response = marketsRepository.getMemberFavoriteMarketList()
                if (response.isSuccessful) {
                    val favoriteMarketIds = response.body()?.response?.marketResDtos?.map { it.marketId } ?: emptyList()
                    _uiState.value = _uiState.value.copy(
                        markets = _uiState.value.markets.map { market ->
                            market.copy(isFavorite = favoriteMarketIds.contains(market.marketId))
                        }
                    )
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MarketListViewModel", "북마크 상태 새로고침 실패", e)
                }
            }
        }
    }
}
