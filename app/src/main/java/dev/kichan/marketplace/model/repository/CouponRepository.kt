package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.CouponService

class CouponRepository {
    private val service: CouponService = NetworkModule.getService(CouponService::class.java)

    suspend fun getCouponList(memberId: Long, marketId: Long, lastCouponId: Long?, pageSize: Long?) =
        service.getCouponList(memberId, marketId, lastCouponId, pageSize)

    suspend fun getPopularCoupon(memberId: Long, lastCouponId: Long?, pageSize: Long?) =
        service.getPopularCoupon(memberId, lastCouponId, pageSize)

    suspend fun getLatestCoupon(memberId: Long, lastCreatedAt: String?, lastCouponId: Long?, pageSize: Int) =
        service.getLatestCoupon(memberId, lastCreatedAt, lastCouponId, pageSize)

    suspend fun getClosingCoupon(pageSize: Int) = service.getClosingCoupon(pageSize)
}