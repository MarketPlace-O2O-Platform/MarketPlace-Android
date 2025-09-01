package dev.kichan.marketplace.model.services

import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import dev.kichan.marketplace.model.dto.*

interface MembersService {

    @GET("/api/members/payback-coupons")
    suspend fun getPaybackCoupon(
        @Query("type") type: String? = null, //ISSUED : 사용가능, ENDED : 끝난
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Query("size") size: Int? = null
    ): Response<CommonResponseCouponPageResIssuedCouponRes>

    @Multipart
    @PUT("/api/members/payback-coupons")
    suspend fun uploadReceipt(
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Part image: MultipartBody.Part
    ): Response<CommonResponseCouponHandleRes>

    @GET("/api/members/coupons")
    suspend fun getCoupons(
        @Query("type") type: String? = null, //ISSUED : 사용가능, ENDED : 끝난
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Query("size") size: Int? = null
    ): Response<CommonResponseCouponPageResIssuedCouponRes>

    @PUT("/api/members/coupons")
    suspend fun useCoupon(@Query("memberCouponId") memberCouponId: Long? = null): Response<CommonResponseCouponHandleRes>

    @GET("/api/members")
    suspend fun getMember(): Response<CommonResponseMemberRes>

    @POST("/api/members")
    suspend fun loginMember(@Body body: MemberLoginReq): Response<CommonResponseString>

    @POST("/api/members/payback-coupons/{couponId}")
    suspend fun downloadPaybackCoupon(@Path("couponId") couponId: Long): Response<CommonResponseObject>

    @POST("/api/members/coupons/{couponId}")
    suspend fun downloadCoupon(@Path("couponId") couponId: Long): Response<Void>

    @PATCH("/api/members/notification/permit")
    suspend fun permitFcmToken(@Body body: MemberFcmReq): Response<CommonResponseObject>

    @PATCH("/api/members/notification/deny")
    suspend fun denyFcmToken(): Response<CommonResponseObject>

    @PATCH("/api/members/admin")
    suspend fun upgradePermission(): Response<CommonResponseObject>

    @PATCH("/api/members/account/permit")
    suspend fun permitAccount(@Body body: MemberAccountReq): Response<CommonResponseObject>

    @PATCH("/api/members/account/deny")
    suspend fun denyAccount(): Response<CommonResponseObject>

    @GET("/api/members/payback-coupons/receipt/{memberCouponId}")
    suspend fun getReceipt(@Path("memberCouponId") memberCouponId: Long): Response<CommonResponseReceiptRes>

}