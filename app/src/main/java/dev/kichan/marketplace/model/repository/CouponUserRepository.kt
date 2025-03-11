package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.CouponUserService

class CouponUserRepository {
    private val service: CouponUserService = NetworkModule.getService(CouponUserService::class.java)

    suspend fun useCoupon(memberCouponId: Long) = service.useCoupon(memberCouponId)
    suspend fun createUserCoupon(couponId: Long) = service.createUserCoupon(couponId)
    suspend fun getMemberCoupon(memberCouponId: Long) = service.getMemberCoupon(memberCouponId)
    suspend fun getMemberCoupons(type: String?, lastMemberCouponId: Long?, pageSize: Int?) =
        service.getMemberCoupons(type, lastMemberCouponId, pageSize)
}