package dev.kichan.marketplace.model.repository

import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton
import dev.kichan.marketplace.model.services.MembersService
import dev.kichan.marketplace.model.dto.*

@Singleton
class MembersRepository @Inject constructor(
    private val service: MembersService
) {

    suspend fun getPaybackCoupon(
        @Query("type") type: String? = null,
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Query("size") size: Int? = null
    ): Response<CommonResponsePaybackRes> {
        return service.getPaybackCoupon(type, memberCouponId, size)
    }

    suspend fun uploadReceipt(
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Part image: MultipartBody.Part
    ): Response<CommonResponseCouponHandleRes> {
        return service.uploadReceipt(memberCouponId, image)
    }

    suspend fun getCoupons(
        @Query("type") type: String? = null,
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Query("size") size: Int? = null
    ): Response<CommonResponseCouponPageResIssuedCouponRes> {
        return service.getCoupons(type, memberCouponId, size)
    }

    suspend fun updateCoupon_2(@Query("memberCouponId") memberCouponId: Long? = null): Response<CommonResponseCouponHandleRes> {
        return service.updateCoupons(memberCouponId)
    }

    suspend fun getMember(): Response<CommonResponseMemberRes> {
        return service.getMember()
    }

    suspend fun loginMember(@Body body: MemberLoginReq): Response<CommonResponseString> {
        return service.loginMember(body)
    }

    suspend fun issuedCoupon(@Path("couponId") couponId: Long): Response<CommonResponseObject> {
        return service.issuedCoupon(couponId)
    }

    suspend fun issuedCoupon_1(@Path("couponId") couponId: Long): Response<CommonResponseObject> {
        return service.issuedCoupon_1(couponId)
    }

    suspend fun permitFcmToken(@Body body: MemberFcmReq): Response<CommonResponseObject> {
        return service.permitFcmToken(body)
    }

    suspend fun denyFcmToken(): Response<CommonResponseObject> {
        return service.denyFcmToken()
    }

    suspend fun upgradePermission(): Response<CommonResponseObject> {
        return service.upgradePermission()
    }

    suspend fun permitAccount(@Body body: MemberAccountReq): Response<CommonResponseObject> {
        return service.permitAccount(body)
    }

    suspend fun denyAccount(): Response<CommonResponseObject> {
        return service.denyAccount()
    }

    suspend fun getReceipt(@Path("memberCouponId") memberCouponId: Long): Response<CommonResponseReceiptRes> {
        return service.getReceipt(memberCouponId)
    }

}