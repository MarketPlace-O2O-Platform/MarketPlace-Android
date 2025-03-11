package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.CouponHandleRes
import dev.kichan.marketplace.model.data.coupon.CouponPagination
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CouponUserService {
    @PUT("/api/members/coupons")
    suspend fun useCoupon(
        @Query("memberCouponId") memberCouponId: Long
    ) : Response<ResponseTemplate<CouponHandleRes>>

    @POST("/api/members/coupons/{couponId}")
    suspend fun createUserCoupon(
        @Path("couponId") couponId: Long,
    )

    @GET("/api/members/coupons/{memberCouponId}")
    suspend fun getMemberCoupon(
        @Path("memberCouponId") memberCouponId: Long
    ) : Response<ResponseTemplate<IssuedCouponRes>>

    @GET("/api/members/coupons/valid")
    suspend fun getMemberCoupons(
        @Query("type") type: String?,
        @Query("memberCouponId") lastMemberCouponId: Long?,
        @Query("size") pageSize: Int?
    ) : Response<ResponseTemplate<CouponPagination<IssuedCouponRes>>>
}