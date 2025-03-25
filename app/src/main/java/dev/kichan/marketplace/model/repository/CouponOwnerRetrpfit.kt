package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.coupon.CouponCreateReq
import dev.kichan.marketplace.model.data.coupon.CouponUpdateReq
import dev.kichan.marketplace.model.service.*

class CouponOwnerRepository {
    private val service: CouponOwnerService = NetworkModule.getService(CouponOwnerService::class.java)

    suspend fun getCoupon(couponId: Long) = service.getCoupon(couponId)
    suspend fun updateCoupon(couponId: Long, body: CouponUpdateReq) = service.updateCoupon(couponId, body)
    suspend fun deleteCoupon(couponId: Long) = service.deleteCoupon(couponId)
    suspend fun updateHiddenCoupon(couponId: Long) = service.updateHiddenCoupon(couponId)
    suspend fun getAllCouponByMarket(marketId: Int, size: Int? = null) = service.getAllCouponByMarket(marketId, size)
    suspend fun createCoupon(body: CouponCreateReq, marketId: Int) = service.createCoupon(body, marketId)
}