package dev.kichan.marketplace.model.service

// ✅ 2. Retrofit API 인터페이스(CouponApiService) 추가

import dev.kichan.marketplace.model.data.CouponListResponse
import dev.kichan.marketplace.model.data.CouponResponse
import dev.kichan.marketplace.model.data.CouponUseResponse
import dev.kichan.marketplace.model.data.ResponseTemplate
import retrofit2.Response
import retrofit2.http.*

interface CouponApiService {
    // ✅ 1. 쿠폰 리스트 조회 (GET) - ResponseTemplate으로 감싸기
    @GET("/api/members/coupons")
    suspend fun getCoupons(
        @Query("type") type: String = "ISSUED",
        @Query("size") size: Int = 10,
        @Header("Authorization") token: String
    ): Response<ResponseTemplate<CouponListResponse>> // ✅ 변경됨!

    // ✅ 2. 쿠폰 사용 처리 (PUT)
    @PUT("/api/members/coupons")
    suspend fun useCoupon(
        @Query("memberCouponId") memberCouponId: Long,
        @Header("Authorization") token: String
    ): Response<ResponseTemplate<CouponUseResponse>> // ✅ 변경됨!

    // ✅ 3. 쿠폰 발급 (POST)
    @POST("/api/members/coupons/{couponId}")
    suspend fun issueCoupon(
        @Path("couponId") couponId: Long,
        @Header("Authorization") token: String
    ): Response<ResponseTemplate<Unit>> // ✅ 변경됨!

    // ✅ 4. 쿠폰 상세 조회 (GET)
    @GET("/api/members/coupons/{memberCouponId}")
    suspend fun getCouponDetail(
        @Path("memberCouponId") memberCouponId: Long,
        @Header("Authorization") token: String
    ): Response<ResponseTemplate<CouponResponse>> // ✅ 변경됨!
}
