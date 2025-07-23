package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.PageNationTemplate
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.payback.PayBack
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PaybackCouponsManage {
    @GET("/api/admin/payback-coupons")
    suspend fun getPaybackCoupons(
        @Query("marketId")
        marketId: Long,

        @Query("couponId")
        couponId: Long? = null,

        @Query("size")
        size: Long? = null
    ) : Response<ResponseTemplate<PageNationTemplate<PayBack>>>
}