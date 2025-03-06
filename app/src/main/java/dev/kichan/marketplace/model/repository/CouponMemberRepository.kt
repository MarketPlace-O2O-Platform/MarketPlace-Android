package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.memberCoupon.MemberCoupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.memberCoupon.MemberCouponUseRes
import retrofit2.Response

interface CouponMemberRepository {
    suspend fun useCoupon(
        memberCouponId: Long,
    ): Response<ResponseTemplate<MemberCouponUseRes>>

    suspend fun issuanceCoupon(
        couponId: Long,
        memberId: Int,
    )

    suspend fun getMemberCoupon(
        memberCouponId: Long
    ): Response<ResponseTemplate<MemberCoupon>>

    suspend fun getValidMemberCoupons(
        memberId: Int
    ): Response<ResponseTemplate<List<MemberCoupon>>>

    suspend fun getUsedMemberCoupons(
        memberId: Int
    ): Response<ResponseTemplate<List<MemberCoupon>>>

    suspend fun getExpiredMemberCoupons(
        memberId: Int
    ): Response<ResponseTemplate<List<MemberCoupon>>>
}