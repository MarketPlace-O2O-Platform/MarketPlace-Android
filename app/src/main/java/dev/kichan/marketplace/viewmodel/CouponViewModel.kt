package dev.kichan.marketplace.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.data.CouponListResponse
import dev.kichan.marketplace.model.data.CouponResponse
import dev.kichan.marketplace.model.service.CouponApiService
import dev.kichan.marketplace.model.NetworkModule
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CouponViewModel : ViewModel() {
    private val couponService: CouponApiService = NetworkModule.getCouponService()

    private val _coupons = MutableLiveData<List<CouponResponse>>() // ✅ 초기값 설정
    val coupons: LiveData<List<CouponResponse>> = _coupons

    private val _hasNext = MutableLiveData<Boolean>()
    val hasNext: LiveData<Boolean> = _hasNext

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _couponUsed = MutableLiveData<Boolean>()
    val couponUsed: LiveData<Boolean> = _couponUsed

    init {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDIyMDE0NjkiLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTc0MjM4ODA5MCwiZXhwIjoxNzQ0OTgwMDkwfQ.anjETPfYxY_qQFhj6abyk4GYurt67hnEwve5YhvyhpU"
        fetchCoupons(token) // ✅ 앱 실행 시 자동으로 쿠폰 리스트 불러오기
    }

    /**
     * ✅ 쿠폰 리스트 가져오기
     */
    fun fetchCoupons(token: String, type: String = "ISSUED") {
        viewModelScope.launch {
            try {
                Log.d("CouponViewModel", "🚀 API 요청: type=$type, token=$token")

                val response = couponService.getCoupons(type, 10, token)

                if (response.isSuccessful) {
                    response.body()?.let {
                        val couponList = it.response.couponResDtos ?: emptyList() // ✅ response 내부 값 가져오기
                        _coupons.postValue(couponList)
                        _hasNext.postValue(it.response.hasNext)

                        Log.d("CouponViewModel", "✅ API 응답: ${it.message}")
                        Log.d("CouponViewModel", "✅ 쿠폰 리스트 로드 완료: $couponList")
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
     */
    fun useCoupon(token: String, memberCouponId: Long) {
        viewModelScope.launch {
            try {
                val response = couponService.useCoupon(memberCouponId, token)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _couponUsed.postValue(it.response.isUsed) // ✅ response 내부 값 가져오기!
                    } ?: _errorMessage.postValue("API 응답이 비어 있음")
                } else {
                    _errorMessage.postValue("쿠폰 사용 오류: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("네트워크 오류: ${e.message}")
            }
        }
    }

}
