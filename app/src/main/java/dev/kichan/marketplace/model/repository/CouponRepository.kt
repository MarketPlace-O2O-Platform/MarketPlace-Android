package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.Coupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponCreateReq
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponUpdateReq
import retrofit2.Response

interface CouponRepository {
    suspend fun createCoupon(body: CouponCreateReq, marketId : Int): Response<ResponseTemplate<Coupon>>
}