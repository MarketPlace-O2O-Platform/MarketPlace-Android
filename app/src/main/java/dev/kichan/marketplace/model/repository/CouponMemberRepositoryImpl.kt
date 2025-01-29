package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.memberCoupon.MemberCoupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.memberCoupon.MemberCouponUseRes
import dev.kichan.marketplace.model.service.CouponMembersService
import retrofit2.Response

class CouponMemberRepositoryImpl : CouponMemberRepository {
    private val memberCouponService = NetworkModule.getService(CouponMembersService::class.java)

    override suspend fun useCoupon(memberCouponId: Long): Response<ResponseTemplate<MemberCouponUseRes>> =
        memberCouponService.useCoupon(memberCouponId = memberCouponId)

    override suspend fun issuanceCoupon(couponId: Long, memberId: Int) =
        memberCouponService.issuanceCoupon(couponId = couponId, memberId = memberId)

    override suspend fun getMemberCoupon(memberCouponId: Long): Response<ResponseTemplate<MemberCoupon>> =
        memberCouponService.getMemberCoupon(memberCouponId = memberCouponId)

    override suspend fun getValidMemberCoupons(memberId: Int): Response<ResponseTemplate<List<MemberCoupon>>> =
        memberCouponService.getValidMemberCoupons(memberId = memberId)

    override suspend fun getUsedMemberCoupons(memberId: Int): Response<ResponseTemplate<List<MemberCoupon>>> =
        memberCouponService.getUsedMemberCoupons(memberId = memberId)

    override suspend fun getExpiredMemberCoupons(memberId: Int): Response<ResponseTemplate<List<MemberCoupon>>> =
        memberCouponService.getExpiredMemberCoupons(memberId = memberId)

}