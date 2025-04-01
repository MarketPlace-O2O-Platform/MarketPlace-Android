package dev.kichan.marketplace.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.CouponResponse
import dev.kichan.marketplace.model.data.coupon.ClosingCouponRes
import dev.kichan.marketplace.model.data.coupon.LatestCouponRes
import dev.kichan.marketplace.model.data.coupon.PopularCouponRes
import dev.kichan.marketplace.model.repository.CouponRepository
import dev.kichan.marketplace.model.service.CouponApiService
import dev.kichan.marketplace.ui.component.atoms.CouponListItemProps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

data class HomeUiState(
    val closingCoupon : List<ClosingCouponRes> = emptyList(),
    val popularCoupons: List<PopularCouponRes> = emptyList(),
    val latestCoupons: List<LatestCouponRes> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

data class CouponListPageState(
    val couponList: List<CouponListItemProps> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class CouponViewModel : ViewModel() {
    private val couponService: CouponApiService = NetworkModule.getCouponService()
    private val couponRepo = CouponRepository()

    var homeState by mutableStateOf(HomeUiState())
    var couponListPageState by mutableStateOf(CouponListPageState())


    fun getClosingCoupon() {
        viewModelScope.launch {
            try {
                homeState = homeState.copy(isLoading = true, errorMessage = null)

                val res = withContext(Dispatchers.IO) {
                    couponRepo.getClosingCoupon(10)
                }

                if (!res.isSuccessful) {
                    homeState = homeState.copy(
                        isLoading = false,
                        errorMessage = "마감 임박 쿠폰을 불러오는 데 실패했어요."
                    )
                    return@launch
                }

                val coupons = res.body()?.response ?: emptyList()
                homeState = homeState.copy(
                    closingCoupon = coupons,
                    isLoading = false
                )
            } catch (e: Exception) {
                homeState = homeState.copy(
                    isLoading = false,
                    errorMessage = "에러 발생: ${e.message}"
                )
            }
        }
    }

    fun getPopularCoupon() {
        viewModelScope.launch {
            try {
                homeState = homeState.copy(isLoading = true)

                val res = withContext(Dispatchers.IO) {
                    couponRepo.getPopularCoupon(null, 20)
                }

                if (res.isSuccessful) {
                    val data = res.body()?.response?.couponResDtos ?: emptyList()
                    homeState = homeState.copy(
                        popularCoupons = data,
                        isLoading = false
                    )
                } else {
                    homeState = homeState.copy(
                        errorMessage = "인기 쿠폰을 불러오지 못했어요.",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                homeState = homeState.copy(
                    errorMessage = "네트워크 오류: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun getLatestCoupon() {
        viewModelScope.launch {
            try {
                homeState = homeState.copy(isLoading = true)

                val res = withContext(Dispatchers.IO) {
                    couponRepo.getLatestCoupon(null, null, 20)
                }

                if (res.isSuccessful) {
                    val data = res.body()?.response?.couponResDtos ?: emptyList()
                    homeState = homeState.copy(
                        latestCoupons = data,
                        isLoading = false
                    )
                } else {
                    homeState = homeState.copy(
                        errorMessage = "최신 쿠폰을 불러오지 못했어요.",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                homeState = homeState.copy(
                    errorMessage = "네트워크 오류: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun getCouponList(type: String) {
        viewModelScope.launch {
            couponListPageState = couponListPageState.copy(isLoading = true, errorMessage = null)

            try {
                val newCoupon: List<CouponListItemProps> = if (type == "popular") {
                    val res = couponRepo.getPopularCoupon(null, 20)
                    if (!res.isSuccessful) {
                        throw Exception("실패!")
                    }
                    res.body()!!
                        .response.couponResDtos.map {
                            it.toCouponListItemProps()
                        }
                } else {
                    val res = couponRepo.getLatestCoupon(null, null, 20)
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


    /////////////////////////////////////////

    // 1) 쿠폰 목록
    private val _coupons = MutableLiveData<List<CouponResponse>>(emptyList())
    val coupons: LiveData<List<CouponResponse>> = _coupons

    // 2) 페이징 여부 (hasNext)
    private val _hasNext = MutableLiveData<Boolean>()
    val hasNext: LiveData<Boolean> = _hasNext

    // 3) 오류 메시지
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // 4) 쿠폰 사용 여부
    private val _couponUsed = MutableLiveData<Boolean>()
    val couponUsed: LiveData<Boolean> = _couponUsed

    // 앱 시작 시 쿠폰 불러오기 (optional)
    init {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDIyMDE0NjkiLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTc0MjM4ODA5MCwiZXhwIjoxNzQ0OTgwMDkwfQ.anjETPfYxY_qQFhj6abyk4GYurt67hnEwve5YhvyhpU"
        fetchCoupons(token)
    }

    /**
     * ✅ 쿠폰 리스트 가져오기
     */
    fun fetchCoupons(token: String, type: String = "ISSUED") {
        viewModelScope.launch {
            try {
                Log.d("CouponViewModel", "🚀 API 요청: type=$type")

                val response = couponService.getCoupons(type, 10, token)

                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        // ResponseTemplate<CouponListResponse> 구조에서
                        // body.response 가 실제 CouponListResponse
                        val couponList = body.response?.couponResDtos ?: emptyList()
                        val next = body.response?.hasNext ?: false

                        _coupons.postValue(couponList)
                        _hasNext.postValue(next)

                        Log.d("CouponViewModel", "✅ API 응답: ${body.message}")
                        Log.d("CouponViewModel", "✅ 쿠폰 리스트 로드 완료: $couponList")
                    } ?: run {
                        // body 자체가 null인 경우
                        _errorMessage.postValue("API 응답이 비어 있음")
                        Log.e("CouponViewModel", "❌ API 응답이 null입니다.")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.postValue("API 오류: $errorBody")
                    Log.e("CouponViewModel", "❌ API 오류 응답: $errorBody")
                }
            } catch (e: HttpException) {
                _errorMessage.postValue("서버 오류: ${e.message}")
                Log.e("CouponViewModel", "❌ 서버 오류: ${e.message}")
            } catch (e: Exception) {
                _errorMessage.postValue("네트워크 오류: ${e.message}")
                Log.e("CouponViewModel", "❌ 네트워크 오류: ${e.message}")
            }
        }
    }

    /**
     * ✅ 쿠폰 사용하기
     *
     * - 서버에 사용 요청
     * - 성공 시, 로컬 coupons LiveData에서 해당 쿠폰의 used = true로 갱신
     */
    fun useCoupon(token: String, memberCouponId: Long) {
        viewModelScope.launch {
            try {
                val response = couponService.useCoupon(memberCouponId, token)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        val isUsed = body.response?.isUsed
                        if (isUsed != null) {
                            _couponUsed.postValue(isUsed)

                            // 서버가 isUsed = true를 주면, 바로 로컬 데이터 업데이트
                            if (isUsed) {
                                val updatedList = _coupons.value?.map { coupon ->
                                    if (coupon.memberCouponId == memberCouponId) {
                                        coupon.copy(used = true)
                                    } else coupon
                                } ?: emptyList()

                                _coupons.postValue(updatedList)
                            }
                        } else {
                            _errorMessage.postValue("쿠폰 사용 응답이 비어 있음")
                        }
                    } ?: run {
                        _errorMessage.postValue("API 응답이 비어 있음")
                    }
                } else {
                    _errorMessage.postValue("쿠폰 사용 오류: ${response.errorBody()?.string()}")
                }
            } catch (e: HttpException) {
                _errorMessage.postValue("서버 오류: ${e.message}")
            } catch (e: Exception) {
                _errorMessage.postValue("네트워크 오류: ${e.message}")
            }
        }
    }
}
