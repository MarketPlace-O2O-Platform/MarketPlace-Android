package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.Coupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.CouponHiddenRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponUpdateReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CouponService {
    @GET("/api/owners/coupons/{couponId}")
    suspend fun getCoupon(
        @Path("couponId") couponId: Long,
    ): Response<ResponseTemplate<Coupon>>

    @PUT("/api/owners/coupons/{couponId}")
    suspend fun updateCoupon(
        @Path("couponId") couponId: Long,
        @Body body: CouponUpdateReq
    ) : Response<ResponseTemplate<Coupon>>

    @DELETE("/api/owners/coupons/{couponId}")
    suspend fun deleteCoupon(
        @Path("couponId") couponId: Long,
    )

    @PUT("/api/owners/coupons/hidden/{couponId}")
    suspend fun updateHiddenCoupon(
        @Path("couponId") couponId: Long,
    ) : Response<ResponseTemplate<CouponHiddenRes>>

    @POST("/api/owners/coupons")
    suspend fun createCoupon(
        @Body body: CouponCreateReq,
        @Query("marketId") marketId: Int,
    ) : Response<ResponseTemplate<Coupon>>
}