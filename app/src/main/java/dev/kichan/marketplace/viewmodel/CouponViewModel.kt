package dev.kichan.marketplace.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import dev.kichan.marketplace.model.data.coupon.ClosingCouponRes
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import dev.kichan.marketplace.model.data.coupon.LatestCouponRes
import dev.kichan.marketplace.model.data.coupon.PopularCouponRes
import dev.kichan.marketplace.model.repository.CouponRepository
import dev.kichan.marketplace.ui.component.atoms.CouponListItemProps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class HomeUiState(
    val closingCoupon : List<ClosingCouponRes> = emptyList(),
    val popularCoupons: List<PopularCouponRes> = emptyList(),
    val latestCoupons: List<LatestCouponRes> = emptyList(),
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

class CouponViewModel : ViewModel() {
    private val couponRepository = CouponRepository()

    var homeState by mutableStateOf(HomeUiState())
    var couponListPageState by mutableStateOf(CouponListPageState())
    var downloadCouponPageState by mutableStateOf(DownloadCouponPageState())


    fun getClosingCoupon() {
        viewModelScope.launch {
            try {
                homeState = homeState.copy(isClosingLoading = true, errorMessage = null)

                val res = withContext(Dispatchers.IO) {
                    couponRepository.getClosingCoupon(10)
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
                    couponRepository.getPopularCoupon(null, 20)
                }

                if (res.isSuccessful) {
                    val data = res.body()?.response?.couponResDtos ?: emptyList()
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
                    couponRepository.getLatestCoupon(null, null, 20)
                }

                if (res.isSuccessful) {
                    val data = res.body()?.response?.couponResDtos ?: emptyList()
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
                    val res = couponRepository.getPopularCoupon(null, 40)
                    if (!res.isSuccessful) {
                        throw Exception("실패!")
                    }
                    res.body()!!
                        .response.couponResDtos.map {
                            it.toCouponListItemProps()
                        }
                } else {
                    val res = couponRepository.getLatestCoupon(null, null, 20)
                    if (!res.isSuccessful) {
                        throw Exception("실패!")
                    }
                    res.body()!!
                        .response.couponResDtos.map {
                            it.toCouponListItemProps()
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
        couponListPageState = couponListPageState.copy(isLoading = true)
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                couponRepository.downloadCoupon(
                    id = id
                )
            }

            if(!res.isSuccessful)
                return@launch

            couponListPageState = couponListPageState.copy(
                couponList = couponListPageState.couponList.map { if(it.id == id) it.copy(isMemberIssued = true) else it.copy() },
                isLoading = false
            )
            homeState = homeState.copy(
                popularCoupons = homeState.popularCoupons.map {
                    if (it.id == id) it.copy(isMemberIssued = true) else it
                },
                latestCoupons = homeState.latestCoupons.map {
                    if (it.id == id) it.copy(isMemberIssued = true) else it
                }
            )
        }
    }

    fun getDownloadCouponList(type: String) {
        couponListPageState = couponListPageState.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                couponRepository.getDownloadCouponList(type = type)
            }

            if(!res.isSuccessful) {
                return@launch
            }

            val body = res.body()!!

            when(type) {
                "ISSUED" -> {
                    downloadCouponPageState = downloadCouponPageState.copy(
                        issuedCouponList = body.response.couponResDtos,
                        isLoading = false,
                    )
                }
                "EXPIRED" -> {
                    downloadCouponPageState = downloadCouponPageState.copy(
                        expiredCouponList = body.response.couponResDtos,
                        isLoading = false,
                    )
                }
                "USED" -> {
                    downloadCouponPageState = downloadCouponPageState.copy(
                        usedCouponList = body.response.couponResDtos,
                        isLoading = false,
                    )
                }
            }

        }
    }
}
