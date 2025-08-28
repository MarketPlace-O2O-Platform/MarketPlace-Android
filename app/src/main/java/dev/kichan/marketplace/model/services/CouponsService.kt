package dev.kichan.marketplace.model.services

import retrofit2.Response
import retrofit2.http.*
import dev.kichan.marketplace.model.dto.*

interface CouponsService {

    @GET("/api/coupons")
    suspend fun getCouponList_4(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResCouponRes>

    @GET("/api/coupons/top/popular")
    suspend fun getPopularCoupon(@Query("pageSize") pageSize: Int? = null): Response<CommonResponseListTopPopularCouponRes>

    @GET("/api/coupons/top/latest")
    suspend fun getLatestCoupon(@Query("pageSize") pageSize: Int? = null): Response<CommonResponseListTopLatestCouponRes>

    @GET("/api/coupons/top/closing")
    suspend fun getClosingCoupon(@Query("pageSize") pageSize: Int? = null): Response<CommonResponseListTopClosingCouponRes>

    @GET("/api/coupons/popular")
    suspend fun getPopularCouponAll(
        @Query("lastIssuedCount") lastIssuedCount: Long? = null,
        @Query("lastCouponId") lastCouponId: Long? = null,
        @Query("pageSize") pageSize: Int? = null
    ): Response<CommonResponseCouponPageResCouponRes>

    @GET("/api/coupons/latest")
    suspend fun getLatestCouponAll(@Query("lastCreatedAt") lastCreatedAt: String? = null, @Query("lastCouponId") lastCouponId: Long? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseCouponPageResCouponRes>

}