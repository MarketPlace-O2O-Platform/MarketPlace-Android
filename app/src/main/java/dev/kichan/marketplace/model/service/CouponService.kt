package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.ClosingCouponRes
import dev.kichan.marketplace.model.data.coupon.CouponPagination
import dev.kichan.marketplace.model.data.coupon.CouponRes
import dev.kichan.marketplace.model.data.coupon.PopularCouponRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CouponService {
    @GET("/api/coupons")
    suspend fun getCouponList(
        @Query("memberId") memberId: Long,
        @Query("marketId") marketId: Long,
        @Query("couponId") lastCouponId: Long?,
        @Query("size") pageSize: Long?
    ): Response<ResponseTemplate<CouponPagination<CouponRes>>>

    @GET("/api/coupons/popular")
    suspend fun getPopularCoupon(
        @Query("memberId") memberId: Long,
        @Query("lastCouponId") lastCouponId: Long?,
        @Query("pageSize") pageSize: Long?
    ): Response<ResponseTemplate<CouponPagination<PopularCouponRes>>>

    @GET("/api/coupons/latest")
    suspend fun getLatestCoupon(
        @Query("memberId") memberId: Long,
        @Query("lastCreatedAt") lastCreatedAt: String?,
        @Query("lastCouponId") lastCouponId: Long?,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseTemplate<CouponPagination<CouponRes>>>

    @GET("/api/coupons/closing")
    suspend fun getClosingCoupon(
        @Query("pageSize") pageSize: Int
    ): Response<ResponseTemplate<List<ClosingCouponRes>>>
}