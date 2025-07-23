package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.PageNationTemplate
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PayBackCouponMember {
    @GET("/api/members/payback-coupons")
    suspend fun getMemberPayBackCoupon(type : String)
    : Response<ResponseTemplate<PageNationTemplate<IssuedCouponRes>>>

    @POST("/api/members/payback-coupons/{id}")
    suspend fun downLoadCoupon(@Path("id") id : Long)
}