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
        @Query("type") type: String? = null, //ISSUED : 사용가능, ENDED : 끝난
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Query("size") size: Int? = null
    ): Response<CommonResponseCouponPageResIssuedCouponRes> {
        return service.getPaybackCoupon(type, memberCouponId, size)
    }

    suspend fun uploadReceipt(
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Part image: MultipartBody.Part
    ): Response<CommonResponseCouponHandleRes> {
        return service.uploadReceipt(memberCouponId, image)
    }

    suspend fun getCoupons(
        @Query("type") type: String? = null, //ISSUED : 사용가능, ENDED : 끝난
        @Query("memberCouponId") memberCouponId: Long? = null,
        @Query("size") size: Int? = null
    ): Response<CommonResponseCouponPageResIssuedCouponRes> {
        return service.getCoupons(type, memberCouponId, size)
    }

    suspend fun useCoupon(@Query("memberCouponId") memberCouponId: Long? = null): Response<CommonResponseCouponHandleRes> {
        return service.useCoupon(memberCouponId)
    }

    suspend fun getMember(): Response<CommonResponseMemberRes> {
        return service.getMember()
    }

    suspend fun loginMember(@Body body: MemberLoginReq): Response<CommonResponseString> {
        return service.loginMember(body)
    }

    suspend fun downloadPaybackCoupon(@Path("couponId") couponId: Long): Response<CommonResponseObject> {
        return service.downloadPaybackCoupon(couponId)
    }

    suspend fun downloadGiftCoupon(@Path("couponId") couponId: Long): Response<Void> {
        return service.downloadGiftCoupon(couponId)
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