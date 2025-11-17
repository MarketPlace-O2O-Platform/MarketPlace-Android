package dev.kichan.marketplace.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dev.kichan.marketplace.BuildConfig
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

data class MarkerGroup(
    val coords: LatLng,
    val markets: List<MarketRes>,
    val count: Int
)

data class MapUiState(
    val markets: List<MarketWithCoords> = emptyList(),
    val markerGroups: List<MarkerGroup> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: LargeCategory = LargeCategory.All,
    val selectedMarketId: Long? = null,
    val selectedGroupMarketIds: List<Long>? = null,
    val visibleBounds: LatLngBounds? = null
)

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val marketsRepository = RepositoryProvider.provideMarketsRepository()
    private val favoritesRepository = RepositoryProvider.provideFavoritesRepository()
    private val kakaoRepository = RepositoryProvider.provideKakaoRepository()

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    private suspend fun getAddress(position: LatLng) : Address? {
        val res = kakaoRepository.coord2Address(
            x = position.longitude.toString(),
            y = position.latitude.toString()
        )

        if(!res.isSuccessful)
            return null

        val documents = res.body()?.documents
        if (documents.isNullOrEmpty())
            return null

        return documents[0]
    }

    fun getMarkets(position : LatLng, bounds: LatLngBounds? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // 1. 여러 지점에서 주소 수집 (중앙점 + 북동쪽)
                val addresses = mutableSetOf<String>()

                // 중앙점 시도
                getAddress(position)?.let { addr ->
                    val formattedAddress = formatProvinceDistrict(addr.address)
                    addresses.add(formattedAddress)
                    Log.d("MapViewModel", "[중앙점 주소] $formattedAddress")
                }

                // 북동쪽 시도 (바텀시트에 가려지지 않는 화면 최상단)
                bounds?.northeast?.let { northeast ->
                    getAddress(northeast)?.let { addr ->
                        val formattedAddress = formatProvinceDistrict(addr.address)
                        addresses.add(formattedAddress)
                        Log.d("MapViewModel", "[북동쪽 주소] $formattedAddress")
                    }
                }

                // 주소를 하나도 못 가져온 경우 (모두 바다 등)
                if (addresses.isEmpty()) {
                    Log.e("MapViewModel", "[주소 없음] 중앙점과 북동쪽 모두 주소 변환 실패")
                    _uiState.value = _uiState.value.copy(markets = emptyList(), markerGroups = emptyList())
                    return@launch
                }

                // 2. 각 주소별로 API 호출 후 병합
                val allMarkets = addresses.map { address ->
                    async {
                        val response = marketsRepository.getMarketsByMap(
                            lastPageIndex = null,
                            category = _uiState.value.selectedCategory.backendLabel,
                            pageSize = 999,
                            address = address
                        )
                        if (response.isSuccessful) {
                            response.body()?.response?.marketResDtos ?: emptyList()
                        } else {
                            emptyList()
                        }
                    }
                }.awaitAll().flatten().distinctBy { it.marketId }

                Log.d("MapViewModel", "[병합 결과] 총 ${allMarkets.size}개 매장 (중복 제거 후)")

                val marketPage = allMarkets
                val marketsWithCoords = marketPage.map { market ->
                    async {
                        try {
                            // 1차: 키워드 검색 시도 (매장명만 사용)
                            val keywordQuery = market.marketName
                            Log.d("MapViewModel", "[키워드 검색 시도] query: $keywordQuery")

                            val keywordResponse = kakaoRepository.searchByKeyword(
                                query = keywordQuery,
                                x = position.longitude.toString(),
                                y = position.latitude.toString(),
                                radius = 20000  // 반경 20km
                            )

                            if (keywordResponse.isSuccessful) {
                                val documents = keywordResponse.body()?.documents
                                Log.d("MapViewModel", "[키워드 검색 결과] ${market.marketName}: ${documents?.size ?: 0}개")

                                val keywordDocument = documents?.firstOrNull()
                                if (keywordDocument != null) {
                                    val lat = keywordDocument.y.toDouble()
                                    val lng = keywordDocument.x.toDouble()
                                    Log.d("MapViewModel", "[키워드 성공] ${market.marketName}: ($lat, $lng)")
                                    return@async MarketWithCoords(market, LatLng(lat, lng))
                                } else {
                                    Log.d("MapViewModel", "[키워드 실패] ${market.marketName}: 결과 없음 → 폴백")
                                }
                            } else {
                                Log.e("MapViewModel", "[키워드 API 실패] ${market.marketName}: ${keywordResponse.code()}")
                            }

                            // 2차: 키워드 검색 실패 → 주소 검색으로 폴백
                            Log.d("MapViewModel", "[주소 검색 시도] ${market.marketName}: ${market.address}")
                            val addressResponse = kakaoRepository.getAddress(market.address)
                            if (addressResponse.isSuccessful) {
                                val addressDocument = addressResponse.body()?.documents?.firstOrNull()
                                if (addressDocument != null) {
                                    val lat = addressDocument.y.toDouble()
                                    val lng = addressDocument.x.toDouble()
                                    Log.d("MapViewModel", "[주소 성공] ${market.marketName}: ($lat, $lng)")
                                    return@async MarketWithCoords(market, LatLng(lat, lng))
                                }
                            }

                            // 3차: 둘 다 실패 → null 반환
                            Log.e("MapViewModel", "[전체 실패] ${market.marketName}: 좌표 없음")
                            null

                        } catch (e: Exception) {
                            Log.e("MapViewModel", "[Exception] ${market.marketName}: ${e.message}", e)
                            null
                        }
                    }
                }.awaitAll().filterNotNull()

                Log.d("MapViewModel", "[API 응답] 카테고리=${_uiState.value.selectedCategory}, 총 매장 수=${marketsWithCoords.size}")
                marketsWithCoords.forEach {
                    Log.d("MapViewModel", "  - ${it.market.marketName} (ID=${it.market.marketId}, 좌표=${it.coords})")
                }

                val markerGroups = groupMarketsByLocation(marketsWithCoords)
                _uiState.value = _uiState.value.copy(
                    markets = marketsWithCoords,
                    markerGroups = markerGroups,
                    visibleBounds = bounds
                )

            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MapViewModel", "지도 매장 로드 실패: position=$position", e)
                }
                _uiState.value = _uiState.value.copy(markets = emptyList())
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

    private fun groupMarketsByLocation(markets: List<MarketWithCoords>): List<MarkerGroup> {
        return markets.groupBy { market ->
            // 소수점 2자리 기준 (약 1km 이내 그룹화)
            val lat = String.format("%.2f", market.coords.latitude)
            val lng = String.format("%.2f", market.coords.longitude)
            "$lat,$lng"
        }.map { (coordKey, marketsAtLocation) ->
            Log.d("MapViewModel", "[그룹화] 좌표=$coordKey, 매장 수=${marketsAtLocation.size}")
            marketsAtLocation.forEach {
                Log.d("MapViewModel", "  - ${it.market.marketName} (ID=${it.market.marketId}, 좌표=${it.coords})")
            }

            // 일관된 정렬 기준 (marketId 오름차순)
            val sortedMarkets = marketsAtLocation.sortedBy { it.market.marketId }
            val selectedMarket = sortedMarkets.first()
            Log.d("MapViewModel", "[대표 선택] ${selectedMarket.market.marketName} (ID=${selectedMarket.market.marketId})")

            MarkerGroup(
                coords = selectedMarket.coords,
                markets = sortedMarkets.map { it.market },
                count = sortedMarkets.size
            )
        }
    }

    fun favorite(marketId: Long) {
        viewModelScope.launch {
            try {
                // 1. 현재 북마크 상태 확인 (UI 토글용)
                val currentMarket = _uiState.value.markets.find { it.market.marketId == marketId }
                val isFavorite = currentMarket?.market?.isFavorite ?: false

                // 2. 서버 토글 API 호출 (POST만 사용)
                val response = favoritesRepository.favorite(marketId)

                // 3. 성공 시에만 UI 토글
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        markets = _uiState.value.markets.map {
                            if(it.market.marketId == marketId)
                                it.copy(market = it.market.copy(isFavorite = !isFavorite))
                            else
                                it
                        }
                    )
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("MapViewModel", "북마크 토글 실패: marketId=$marketId", e)
                }
            }
        }
    }

    fun onCategoryChanged(category: LargeCategory, position: LatLng) {
        Log.d("MapViewModel", "[카테고리 변경] ${_uiState.value.selectedCategory} → $category")
        Log.d("MapViewModel", "[선택 초기화] 이전=${_uiState.value.selectedGroupMarketIds}")

        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            selectedGroupMarketIds = null  // 카테고리 변경 시 선택 상태 초기화
        )
        getMarkets(position)
    }

    fun onMarkerClick(marketId: Long) {
        _uiState.value = _uiState.value.copy(selectedMarketId = marketId)
    }

    fun onMarkerGroupClick(group: MarkerGroup) {
        Log.d("MapViewModel", "[마커 클릭] 좌표=${group.coords}, 매장 수=${group.count}")
        group.markets.forEach {
            Log.d("MapViewModel", "  - ${it.marketName} (ID=${it.marketId})")
        }

        _uiState.value = _uiState.value.copy(
            selectedGroupMarketIds = group.markets.map { it.marketId }
        )
    }

    fun clearSelection() {
        _uiState.value = _uiState.value.copy(
            selectedMarketId = null,
            selectedGroupMarketIds = null
        )
    }
}
