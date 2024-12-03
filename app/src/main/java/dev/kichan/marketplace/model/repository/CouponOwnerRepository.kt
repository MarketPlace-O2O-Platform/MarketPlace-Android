package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.Coupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponCreateReq
import retrofit2.Response

interface CouponOwnerRepository {
    suspend fun createCoupon(
        body: CouponCreateReq,
        marketId: Int
    ): Response<ResponseTemplate<Coupon>>
}