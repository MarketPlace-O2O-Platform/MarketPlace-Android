package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponMember
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CouponUserService {
    @GET("api/coupons/latest/tops")
    suspend fun getLatestTopCoupon(
        @Query("count") count: Int
    ): Response<ResponseTemplate<List<CouponMember>>>
}