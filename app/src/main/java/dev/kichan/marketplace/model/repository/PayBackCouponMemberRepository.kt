package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.PayBackCouponMember

class PayBackCouponMemberRepository {
    private val service = NetworkModule.getService(PayBackCouponMember::class.java)

    suspend fun getMemberPayBackCoupon(type: String) =
        service.getMemberPayBackCoupon(type)

    suspend fun downloadCoupon(id: Long) = service.downLoadCoupon(id)
}