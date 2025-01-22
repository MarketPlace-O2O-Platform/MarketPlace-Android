package dev.kichan.marketplace.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.CouponMemberRes
import dev.kichan.marketplace.model.data.LatestCoupon
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.service.CouponService
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CouponViewModel : ViewModel() {
    private val repository = CouponRepositoryImpl()

    val coupons = MutableLiveData<List<CouponMemberRes>>()
    fun getCoupons(marketId: Long, couponId: Long? = null, size: Int? = null) {
        viewModelScope.launch {
            val res = repository.getCoupons(marketId, couponId, size)

            if(res.isSuccessful) {
                coupons.value = res.body()!!.response.couponResDtos
            }
        }
    }
}

class CouponRepositoryImpl {
    private val service = NetworkModule.getService(CouponService::class.java)

    suspend fun getCoupons(marketId: Long, couponId: Long?, size: Int?) =
        service.getCouponList(marketId, couponId, size)

    suspend fun getLatestTopCoupon(
        lastCreatedAt: LocalDateTime?,
        lastCouponId: Long?,
        pageSize: Int?
    ): Response<ResponseTemplate<List<LatestCoupon>>> {
        return service.getLatestCoupon(
            if (lastCreatedAt != null) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                lastCreatedAt.format(formatter)
            } else null,
            lastCouponId,
            pageSize
        )
    }

    suspend fun getClosingCoupon(pageSize: Int?) = service.getClosingCoupon(pageSize)
}