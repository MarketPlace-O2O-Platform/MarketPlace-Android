package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.CouponListResponse
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import dev.kichan.marketplace.model.data.payback.CouponHandleRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PayBackCouponMember {
    @GET("/api/members/payback-coupons")
    suspend fun getMemberPayBackCoupon(@Query("type") type : String)
    : Response<ResponseTemplate<CouponListResponse<IssuedCouponRes>>>

    @PUT("/api/members/payback-coupons")
    suspend fun uploadReceipt(
        @Query("memberCouponId")
        memberCouponId : Long
    ): Response<ResponseTemplate<CouponHandleRes>>

    @POST("/api/members/payback-coupons/{id}")
    suspend fun downLoadCoupon(@Path("id") id : Long)
}