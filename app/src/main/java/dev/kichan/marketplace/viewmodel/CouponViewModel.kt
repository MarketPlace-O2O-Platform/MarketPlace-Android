package dev.kichan.marketplace.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import dev.kichan.marketplace.model.data.TopClosingCouponRes
import dev.kichan.marketplace.model.data.IssuedCouponRes
import dev.kichan.marketplace.model.data.TopLatestCouponRes
import dev.kichan.marketplace.model.data.TopPopularCouponRes
import dev.kichan.marketplace.model.repository.CouponRepository
import dev.kichan.marketplace.model.repository.CouponRepositoryImpl
import dev.kichan.marketplace.model.service.CouponService
import dev.kichan.marketplace.ui.component.atoms.CouponListItemProps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class HomeUiState(
    val closingCoupon : List<TopClosingCouponRes> = emptyList(),
    val popularCoupons: List<TopPopularCouponRes> = emptyList(),
    val latestCoupons: List<TopLatestCouponRes> = emptyList(),
    val isClosingLoading: Boolean = false,
    val isPopularLoading: Boolean = false,
    val isLatestLoading: Boolean = false,
    val errorMessage: String? = null,
)

data class CouponListPageState(
    val couponList: List<CouponListItemProps> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class DownloadCouponPageState(
    val issuedCouponList : List<IssuedCouponRes> = emptyList(),
    val expiredCouponList : List<IssuedCouponRes> = emptyList(),
    val usedCouponList : List<IssuedCouponRes> = emptyList(),
    val isLoading : Boolean = false,
)

sealed class HomeNavigationEvent {
    object NavigateToSearch : HomeNavigationEvent()
    data class NavigateToEventDetail(val marketId: String) : HomeNavigationEvent()
    data class NavigateToCouponListPage(val type: String) : HomeNavigationEvent()
}

class CouponViewModel(
    private val couponRepository: CouponRepository = CouponRepositoryImpl(NetworkModule.getService(CouponService::class.java))
) : ViewModel() {

    var homeState by mutableStateOf(HomeUiState(
        closingCoupon = emptyList(),
        popularCoupons = emptyList(),
        latestCoupons = emptyList()
    ))
    var couponListPageState by mutableStateOf(CouponListPageState())
    var downloadCouponPageState by mutableStateOf(DownloadCouponPageState())

    private val _navigationEvent = MutableSharedFlow<HomeNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    fun getClosingCoupon() {
        viewModelScope.launch {
            try {
                homeState = homeState.copy(isClosingLoading = true, errorMessage = null)

                val res = withContext(Dispatchers.IO) {
                    couponRepository.getClosingCoupon(pageSize = 10)
                }

                if (!res.isSuccessful) {
                    homeState = homeState.copy(
                        isClosingLoading = false,
                        errorMessage = "마감 임박 쿠폰을 불러오는 데 실패했어요."
                    )
                    return@launch
                }

                val coupons = res.body()?.response ?: emptyList()
                homeState = homeState.copy(
                    closingCoupon = coupons,
                    isClosingLoading = false
                )
            } catch (e: Exception) {
                homeState = homeState.copy(
                    isClosingLoading = false,
                    errorMessage = "에러 발생: ${e.message}"
                )
            }
        }
    }

    fun getPopularCoupon() {
        viewModelScope.launch {
            try {
                homeState = homeState.copy(isPopularLoading = true)

                val res = withContext(Dispatchers.IO) {
                    couponRepository.getPopularCoupon(pageSize = 20)
                }

                if (res.isSuccessful) {
                    val data = res.body()?.response ?: emptyList()
                    homeState = homeState.copy(
                        popularCoupons = data,
                        isPopularLoading = false
                    )
                } else {
                    homeState = homeState.copy(
                        errorMessage = "인기 쿠폰을 불러오지 못했어요.",
                        isPopularLoading = false
                    )
                }
            } catch (e: Exception) {
                homeState = homeState.copy(
                    errorMessage = "네트워크 오류: ${e.message}",
                    isPopularLoading = false
                )
            }
        }
    }

    fun getLatestCoupon() {
        viewModelScope.launch {
            try {
                homeState = homeState.copy(isLatestLoading = true)

                val res = withContext(Dispatchers.IO) {
                    couponRepository.getLatestCoupon(pageSize = 20)
                }

                if (res.isSuccessful) {
                    val data = res.body()?.response ?: emptyList()
                    homeState = homeState.copy(
                        latestCoupons = data,
                        isLatestLoading = false
                    )
                } else {
                    homeState = homeState.copy(
                        errorMessage = "최신 쿠폰을 불러오지 못했어요.",
                        isLatestLoading = false
                    )
                }
            } catch (e: Exception) {
                homeState = homeState.copy(
                    errorMessage = "네트워크 오류: ${e.message}",
                    isLatestLoading = false
                )
            }
        }
    }

    fun getCouponList(type: String) {
        viewModelScope.launch {
            couponListPageState = couponListPageState.copy(isLoading = true, errorMessage = null)

            try {
                val newCoupon: List<CouponListItemProps> = if (type == "popular") {
                    val res = couponRepository.getPopularCoupon_1(pageSize = 40)
                    if (!res.isSuccessful) {
                        throw Exception("실패!")
                    }
                    res.body()!!.response.couponResDtos.map {
                            // it.toCouponListItemProps() // Removed, needs re-mapping
                            CouponListItemProps(
                                id = it.couponId,
                                name = it.couponName,
                                marketName = it.marketName,
                                imageUrl = it.thumbnail, // Assuming thumbnail is direct URL
                                address = "", // Not available in new DTO
                                isAvailable = true, // Not available in new DTO
                                isMemberIssued = false, // Not available in new DTO
                                marketId = it.marketId
                            )
                        }
                } else {
                    val res = couponRepository.getLatestCoupon_1(pageSize = 20)
                    if (!res.isSuccessful) {
                        throw Exception("실패!")
                    }
                    res.body()!!.response.couponResDtos.map {
                            // it.toCouponListItemProps() // Removed, needs re-mapping
                            CouponListItemProps(
                                id = it.couponId,
                                name = it.couponName,
                                marketName = it.marketName,
                                imageUrl = it.thumbnail, // Assuming thumbnail is direct URL
                                address = "", // Not available in new DTO
                                isAvailable = true, // Not available in new DTO
                                isMemberIssued = false, // Not available in new DTO
                                marketId = it.marketId
                            )
                        }
                }

                Log.d("newCoupon", newCoupon.toString())

                couponListPageState = couponListPageState.copy(
                    couponList = newCoupon.map { it.copy() },
                    isLoading = false
                )
            } catch (e: Exception) {
                couponListPageState = couponListPageState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "알 수 없는 오류"
                )
            }
        }
    }

    fun downloadCoupon(id: Long) {
        // This method needs to be re-implemented based on the new API
        // The old couponRepository.downloadCoupon(id) is not available.
        // Check apiDocs.json for issuedCoupon or issuedCoupon_1
        viewModelScope.launch {
            Log.d("CouponViewModel", "downloadCoupon called for id: $id - needs re-implementation")
        }
    }

    fun getDownloadCouponList(type: String) {
        // This method needs to be re-implemented based on the new API
        // The old couponRepository.getDownloadCouponList(type) is not available.
        // Check apiDocs.json for getCouponList_2 or getCouponList_3
        viewModelScope.launch {
            Log.d("CouponViewModel", "getDownloadCouponList called for type: $type - needs re-implementation")
        }
    }

    fun onSearchClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToSearch)
        }
    }

    fun onEventDetailClicked(marketId: String) {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToEventDetail(marketId))
        }
    }

    fun onCouponListPageClicked(type: String) {
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToCouponListPage(type))
        }
    }
}