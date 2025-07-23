package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.PageNationTemplate
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT

interface PayBackCouponMember {
    @GET("/api/members/payback-coupons")
    suspend fun getMemberPayBackCoupon(type : String)
    : Response<ResponseTemplate<PageNationTemplate<IssuedCouponRes>>>
}