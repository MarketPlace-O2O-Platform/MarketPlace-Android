package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.Coupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service.CouponOwnerService
import retrofit2.Response

class CouponOwnerRepositoryImpl : CouponOwnerRepository {
    private val service = NetworkModule.getService(CouponOwnerService::class.java)

    override suspend fun createCoupon(
        body: CouponCreateReq,
        marketId: Int
    ): Response<ResponseTemplate<Coupon>> {
        return service.createCoupon(body, marketId)
    }
}