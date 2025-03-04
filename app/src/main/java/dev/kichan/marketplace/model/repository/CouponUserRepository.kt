package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.CouponUserService

class CouponUserRepository {
    private val service: CouponUserService = NetworkModule.getService(CouponUserService::class.java)

    suspend fun useCoupon(memberCouponId: Long) = service.useCoupon(memberCouponId)
    suspend fun createUserCoupon(memberId: Long, couponId: Long) = service.createUserCoupon(memberId, couponId)
    suspend fun getMemberCoupon(memberCouponId: Long) = service.getMemberCoupon(memberCouponId)
    suspend fun getMemberCoupons(memberId: Long, type: String?, lastMemberCouponId: Long?, pageSize: Int?) =
        service.getMemberCoupons(memberId, type, lastMemberCouponId, pageSize)
}