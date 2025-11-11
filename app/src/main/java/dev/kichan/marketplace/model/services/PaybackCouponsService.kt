package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponseCouponPageResPaybackRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PaybackCouponsService {

    @GET("/api/payback-coupons")
    suspend fun getCouponList(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResPaybackRes>

}