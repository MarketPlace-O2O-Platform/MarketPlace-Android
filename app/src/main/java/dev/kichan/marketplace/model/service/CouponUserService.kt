package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponMember
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CouponUserService {
    @GET("api/coupons")
    suspend fun getCouponList(
        @Query("marketId") marketId: String
    ): Response<ResponseTemplate<List<CouponMember>>>

    @GET("api/coupons/latest")
    suspend fun getLatestCoupon(
        @Query("lastPageIndex") lastPageIndex: Int,
        @Query("lastModified") lastModified: String,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseTemplate<List<CouponMember>>>

    @GET("api/coupons/latest/tops")
    suspend fun getLatestTopCoupon(
        @Query("count") count: Int
    ): Response<ResponseTemplate<List<CouponMember>>>

    @GET("api/coupons/closing")
    suspend fun getClosingCoupon(
        @Query("count") count: Int
    ): Response<ResponseTemplate<List<CouponMember>>>
}