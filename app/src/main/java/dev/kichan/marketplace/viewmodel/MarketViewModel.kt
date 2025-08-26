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
import dev.kichan.marketplace.model.data.coupon.CouponRes
import dev.kichan.marketplace.model.dto.kakao.adress.Address
import dev.kichan.marketplace.model.data.kakao.adress.LotNumberAddress
import dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.model.repository.CouponOwnerRepository

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

data class MyPageUiState(
    val favorites : List<MarketRes> = emptyList(),
    val isLoading: Boolean = false,
)

data class MarketDetailPageUiState(
    val marketData : MarketDetailRes? = null,
    val couponList : List<CouponRes> = emptyList(),
    val isLoading: Boolean = false
)

class MarketViewModel : ViewModel() {
    private val marketRepository = MarketRepository()
    private val favoriteRepository = FavoritesRepository()
    private val kakaoService = NetworkModule.getKakaoService()
    private val couponOwnerRepository = CouponOwnerRepository()

    var marketPageUiState by mutableStateOf(MarketPageUiState())
    var mapPageState by mutableStateOf(MapPageUiState())
    var myPageUiState by mutableStateOf(MyPageUiState())
    var marketDetailPageUiState by mutableStateOf(MarketDetailPageUiState())

    fun getMarketsData(category: LargeCategory, isInit: Boolean, lastMarketId: String?) {
        viewModelScope.launch {
            marketPageUiState = marketPageUiState.copy(isLoading = true, marketData = if(isInit) emptyList() else marketPageUiState.marketData)

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

    fun getMarket(id: Long) {
        marketDetailPageUiState = marketDetailPageUiState.copy(isLoading = true)

        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                marketRepository.getMarket(id)
            }

            if(!res.isSuccessful) {
                throw Exception("오류")
            }

            val body = res.body()!!
            marketDetailPageUiState = marketDetailPageUiState.copy(marketData = body.response, isLoading = false)
        }
    }

    fun getMarketCoupon(id: Long) {
        marketDetailPageUiState = marketDetailPageUiState.copy(isLoading = true)
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                couponOwnerRepository.getAllCouponByMarket(id.toInt(), 100)
            }

            if(!res.isSuccessful) {
                throw Exception("쿠폰 조회 에러")
            }

            val body = res.body()!!
            marketDetailPageUiState = marketDetailPageUiState.copy(
                couponList = body.response.couponResDtos,
                isLoading = false
            )
        }
    }

    private suspend fun getAddress(position: LatLng) : Address {
        val res = kakaoService.coord2Address(
            x = position.longitude.toString(),
            y = position.latitude.toString()
        )

        if(!res.isSuccessful)
            throw Exception("주소 가져오는 문제")

        return res.body()!!.documents[0]
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

    fun getMarketByPosition(position : LatLng, category: LargeCategory) {
        mapPageState = mapPageState.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val address = formatProvinceDistrict(getAddress(position).address)
            val res = withContext(Dispatchers.IO) {
                marketRepository.getMarketByAddress(
                    address = address,
                    lastMarketId = null,
                    category = if(category == LargeCategory.All) null else category.backendLabel,
                    pageSize = null
                )
            }

            if(!res.isSuccessful){
                throw Exception(res.message().toString())
            }

            val newMarket = res.body()!!.response.marketResDtos
                .filter { !mapPageState.marketData.contains(it) }

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
                marketData = newMarket + mapPageState.marketData,
                positionList = positionList + mapPageState.positionList,
                isLoading = false
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

    fun getFavorites() {
        myPageUiState = myPageUiState.copy(isLoading = true)
        viewModelScope.launch {
            val res = marketRepository.getFavoriteMarket(
                lastMarketId = null,
                pageSize = 100000
            )
            if (!res.isSuccessful) {
                return@launch
            }

            val myFavorites = res.body()!!.response.marketResDtos
            myPageUiState = myPageUiState.copy(isLoading = false, favorites = myFavorites)
        }
    }
}