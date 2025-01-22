package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ClosingCouponRes
import dev.kichan.marketplace.model.data.CouponMemberRes
import dev.kichan.marketplace.model.data.LatestCoupon
import dev.kichan.marketplace.model.data.ResponseTemplate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

data class CouponPageNation(
    val couponResDtos: List<CouponMemberRes>,
    val hasNext: Boolean
)

interface CouponService {
    @GET("api/coupons")
    suspend fun getCouponList(
        @Query("marketId") marketId: Long,
        @Query("couponId") couponId: Long?,
        @Query("size") size: Int?
    ): Response<ResponseTemplate<CouponPageNation>>

    @GET("api/coupons/latest")
    suspend fun getLatestCoupon(
        @Query("lastCreatedAt") lastCreatedAt : String?,
        @Query("lastCouponId") lastCouponId : Long?,
        @Query("pageSize") pageSize: Int?
    ): Response<ResponseTemplate<List<LatestCoupon>>>

    @GET("api/coupons/closing")
    suspend fun getClosingCoupon(
        @Query("pageSize") pageSize: Int?
    ): Response<ResponseTemplate<List<ClosingCouponRes>>>
}