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

    /**
     * ✅ 쿠폰 리스트 가져오기
     */
    fun fetchCoupons(token: String, type: String = "ISSUED", memberCouponId: Long? = null) {
        viewModelScope.launch {
            try {
                val response = couponService.getCoupons(type, memberCouponId, 10, token)
                if (response.isSuccessful) {
                    response.body()?.let {
                        // ✅ `couponResDtos`가 존재하는지 확인 후 변환
                        val couponList = it.couponResDtos ?: emptyList() // ✅ 중간에 response 제거
                        _coupons.postValue(couponList)
                        _hasNext.postValue(it.hasNext)
                    }
                } else {
                    _errorMessage.postValue("API 오류: ${response.errorBody()?.string()}")
                }
            } catch (e: HttpException) {
                _errorMessage.postValue("서버 오류: ${e.message}")
            } catch (e: Exception) {
                _errorMessage.postValue("네트워크 오류: ${e.message}")
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
                        _couponUsed.postValue(it.isUsed)
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
