package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.LatestCoupon
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.service.CouponPageNation
import dev.kichan.marketplace.model.service.CouponService
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CouponRepositoryImpl {
    private val service = NetworkModule.getService(CouponService::class.java)

    suspend fun getCoupons(marketId: Long, couponId: Long?, size: Int?) =
        service.getCouponList(marketId, couponId, size)

    suspend fun getLatestTopCoupon(
        lastCreatedAt: LocalDateTime?,
        lastCouponId: Long?,
        pageSize: Int?
    ): Response<ResponseTemplate<CouponPageNation<LatestCoupon>>> {
        return service.getLatestCoupon(
            if (lastCreatedAt != null) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                lastCreatedAt.format(formatter)
            } else null,
            lastCouponId,
            pageSize
        )
    }

    suspend fun getClosingCoupon(pageSize: Int?) = service.getClosingCoupon(pageSize)
}