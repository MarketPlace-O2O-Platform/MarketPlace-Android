package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.memberCoupon.MemberCoupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.memberCoupon.MemberCouponUseRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MemberCouponService {
    @PUT("/api/members/coupons/{memberCouponId}")
    suspend fun useCoupon(
        @Path("memberCouponId") memberCouponId: Long,
    ) : Response<ResponseTemplate<MemberCouponUseRes>>


    @POST("/api/members/coupons/{couponId}")
    suspend fun issuanceCoupon(
        @Path("couponId") couponId: Long,
        @Query("memberId") memberId: Int,
    )

    @GET("/api/members/coupons/{memberCouponId}")
    suspend fun getMemberCoupon(
        @Path("memberCouponId") memberCouponId: Long
    ) : Response<ResponseTemplate<MemberCoupon>>

    @GET("/api/members/coupons/valid")
    suspend fun getValidMemberCoupons(
        @Query("memberId") memberId: Int
    ) : Response<ResponseTemplate<List<MemberCoupon>>>

    @GET("/api/members/coupons/used")
    suspend fun getUsedMemberCoupons(
        @Query("memberId") memberId: Int
    ) : Response<ResponseTemplate<List<MemberCoupon>>>


    @GET("/api/members/coupons/expired")
    suspend fun getExpiredMemberCoupons(
        @Query("memberId") memberId: Int
    ) : Response<ResponseTemplate<List<MemberCoupon>>>
}