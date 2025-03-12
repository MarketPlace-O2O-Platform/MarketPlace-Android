package dev.kichan.marketplace.model.service

// ✅ 2. Retrofit API 인터페이스(CouponApiService) 추가

import dev.kichan.marketplace.model.data.CouponListResponse
import dev.kichan.marketplace.model.data.CouponResponse
import dev.kichan.marketplace.model.data.CouponUseResponse
import retrofit2.Response
import retrofit2.http.*

interface CouponApiService {
    // ✅ 1. 쿠폰 리스트 조회 (GET)
    @GET("/api/members/coupons")
    suspend fun getCoupons(
        @Query("type") type: String = "ISSUED", // 기본값: 사용 가능한 쿠폰
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Query("size") size: Int = 10,
        @Header("Authorization") token: String
    ): Response<CouponListResponse>

    // ✅ 2. 쿠폰 사용 처리 (PUT)
    @PUT("/api/members/coupons")
    suspend fun useCoupon(
        @Query("memberCouponId") memberCouponId: Long,
        @Header("Authorization") token: String
    ): Response<CouponUseResponse>

    // ✅ 3. 쿠폰 발급 (POST)
    @POST("/api/members/coupons/{couponId}")
    suspend fun issueCoupon(
        @Path("couponId") couponId: Long,
        @Header("Authorization") token: String
    ): Response<Unit>

    // ✅ 4. 쿠폰 상세 조회 (GET)
    @GET("/api/members/coupons/{memberCouponId}")
    suspend fun getCouponDetail(
        @Path("memberCouponId") memberCouponId: Long,
        @Header("Authorization") token: String
    ): Response<CouponResponse>
}
