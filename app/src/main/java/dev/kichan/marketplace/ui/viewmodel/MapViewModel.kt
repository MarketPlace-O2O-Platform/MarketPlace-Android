package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.data.kakao.adress.LotNumberAddress
import dev.kichan.marketplace.model.data.remote.RepositoryProvider
import dev.kichan.marketplace.model.dto.MarketRes
import dev.kichan.marketplace.model.dto.kakao.adress.Address
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

    private suspend fun getAddress(position: LatLng) : Address {
        val res = kakaoRepository.coord2Address(
            x = position.longitude.toString(),
            y = position.latitude.toString()
        )

        if(!res.isSuccessful)
            throw Exception("주소 가져오는 문제")

        return res.body()!!.documents[0]
    }

    fun getMarkets(position : LatLng) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val address = formatProvinceDistrict(getAddress(position).address)
            try {
                val response = marketsRepository.getMarketsByMap(
                    lastPageIndex = null,
                    category = _uiState.value.selectedCategory.backendLabel,
                    pageSize = 999,
                    address = address
                )
                if (!response.isSuccessful) {
                    return@launch
                }
                val marketPage = response.body()?.response?.marketResDtos ?: emptyList()
                val marketsWithCoords = marketPage.map { market ->
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
    private fun formatProvinceDistrict(addr: LotNumberAddress): String {
        val suffixMap = mapOf(
            "서울" to "특별자치도",
            "부산" to "광역시",
            "대구" to "광역시",
            "인천" to "광역시",
            "광주" to "광역시",
            "대전" to "광역시",
            "울산" to "광역시",
            "세종" to "특별자치시",
            "경기" to "도",
            "강원" to "도",
            "충북" to "도",
            "충남" to "도",
            "전북" to "도",
            "전남" to "도",
            "경북" to "도",
            "경남" to "도",
            "제주" to "특별자치도"
        )

        val province = addr.region_1depth_name
        val provinceSuffix = suffixMap[province]
            ?: throw IllegalArgumentException("알 수 없는 시·도: $province")

        return "$province$provinceSuffix ${addr.region_2depth_name}"
    }

    fun favorite(marketId: Long) {
        viewModelScope.launch {
            try {
                favoritesRepository.favorite(marketId)
                _uiState.value = _uiState.value.copy(
                    //todo: 시발 이게 뭔 코드야
                    markets = _uiState.value.markets.map {
                        if(it.market.marketId == marketId)
                            it.copy(market = it.market.copy(isFavorite = !it.market.isFavorite))
                        else
                            it
                    }
                )
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun onCategoryChanged(category: LargeCategory, position: LatLng) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
        getMarkets(position)
    }
}
