package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponseCouponPageResPaybackRes
import dev.kichan.marketplace.model.services.PaybackCouponsService
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaybackCouponsRepository @Inject constructor(
    private val service: PaybackCouponsService
) {
    suspend fun getCouponList(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResPaybackRes> {
        return service.getCouponList(marketId, couponId, size)
    }
}