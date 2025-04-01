package dev.kichan.marketplace.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.model.repository.MarketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MarketPageUiState(
    val marketData: List<MarketRes> = emptyList(),
    val data: List<MarketRes> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MarketViewModel : ViewModel() {
    private val marketRepository = MarketRepository()
    var marketPageUiState by mutableStateOf(MarketPageUiState())

    fun loadMarketData(category : LargeCategory, isInit: Boolean, lastMarketId: String?) {
        viewModelScope.launch {
            marketPageUiState = marketPageUiState.copy(isLoading = true)

            try {
                val res = withContext(Dispatchers.IO) {
                    marketRepository.getMarkets(
                        lastMarketId = if (isInit) null else lastMarketId,
                        category = if (category == LargeCategory.All) null else category.backendLabel,
                        pageSize = 20
                    )
                }

                if (res.isSuccessful) {
                    val body = res.body()?.response?.marketResDtos ?: emptyList()
                    marketPageUiState = marketPageUiState.copy(
                        marketData = body,
                        isLoading = false
                    )
                } else {
                    marketPageUiState = marketPageUiState.copy(
                        isLoading = false,
                        errorMessage = "마켓 데이터를 불러오지 못했습니다"
                    )
                }
            } catch (e: Exception) {
                marketPageUiState = marketPageUiState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "알 수 없는 오류"
                )
            }
        }
    }
}