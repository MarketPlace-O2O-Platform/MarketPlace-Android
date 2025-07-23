package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.PaybackCouponsManage

class PaybackCouponsManageRepository {
    private val service = NetworkModule.getService(PaybackCouponsManage::class.java)

    suspend fun getPaybackCoupons(
        marketId: Long,
        couponId: Long? = null,
        size: Long? = null
    ) = service.getPaybackCoupons(marketId, couponId, size)
}