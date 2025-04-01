package dev.kichan.marketplace.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.model.repository.FavoritesRepository
import dev.kichan.marketplace.model.repository.MarketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.gms.maps.model.LatLng

data class MarketPageUiState(
    val marketData: List<MarketRes> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class MapPageUiState(
    val marketData: List<MarketRes> = emptyList(),
    val positionList: List<LatLng> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MarketViewModel : ViewModel() {
    private val marketRepository = MarketRepository()
    private val favoriteRepository = FavoritesRepository()
    private val kakaoService = NetworkModule.getKakaoService()

    var marketPageUiState by mutableStateOf(MarketPageUiState())
    var mapPageState by mutableStateOf(MapPageUiState())

    fun getMarketData(category: LargeCategory, isInit: Boolean, lastMarketId: String?) {
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

    fun getMarketByAddress(address: String) {
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                marketRepository.getMarketByAddress(
                    address = address,
                    lastMarketId = null,
                    category = null,
                    pageSize = null
                )
            }

            val newMarket = res.body()!!.response.marketResDtos
            val positionList = newMarket.map { kakaoService.getAddress(query = it.address) }
                .filter { it.isSuccessful }
                .map { it.body()!!.documents }
                .filter { it.isNotEmpty() }
                .map {
                    Log.d("Position", it.toString())
                    it[0]
                }
                .map { LatLng(it.y.toDouble(), it.x.toDouble()) }

            mapPageState = mapPageState.copy(
                marketData = newMarket,
                positionList = positionList
            )
        }
    }

    fun favorite(marketId: Long) {
        marketPageUiState = marketPageUiState.copy(isLoading = true)
        try {
            viewModelScope.launch {
                val res = withContext(Dispatchers.IO) {
                    favoriteRepository.favorites(marketId)
                }

                if (!res.isSuccessful) {
                    Log.e("FAVORITE", "실패!")
                    return@launch
                }
                val newMarketData = marketPageUiState.marketData.map {
                    if (it.id == marketId) {
                        it.copy(isFavorite = !it.isFavorite)
                    } else it
                }

                marketPageUiState = marketPageUiState.copy(
                    marketData = newMarketData
                )

                //todo: 이름 수정
                val newMarketData2 = mapPageState.marketData.map {
                    if (it.id == marketId) {
                        it.copy(isFavorite = !it.isFavorite)
                    } else it
                }
                mapPageState = mapPageState.copy(
                    marketData = newMarketData2
                )
            }
        } catch (e: Exception) {
            Log.e("FAVORITE", "favorite: $e")
        } finally {
            marketPageUiState = marketPageUiState.copy(isLoading = false)
        }
    }
}