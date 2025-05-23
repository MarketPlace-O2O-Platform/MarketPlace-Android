package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.CouponService
import retrofit2.http.Path

class CouponRepository {
    private val service: CouponService = NetworkModule.getService(CouponService::class.java)

    suspend fun getCouponList(marketId: Long, lastCouponId: Long?, pageSize: Long?) =
        service.getCouponList(marketId, lastCouponId, pageSize)

    suspend fun getPopularCoupon(lastCouponId: Long?, pageSize: Long?) =
        service.getPopularCoupon(lastCouponId, pageSize)

    suspend fun getLatestCoupon(lastCreatedAt: String?, lastCouponId: Long?, pageSize: Int) =
        service.getLatestCoupon(lastCreatedAt, lastCouponId, pageSize)

    suspend fun getClosingCoupon(pageSize: Int) = service.getClosingCoupon(pageSize)

    suspend fun getDownloadCouponList(
        @Path("type") type : String? = null
    ) = service.getDownloadCouponList(type = type)

    suspend fun downloadCoupon(id : Long) = service.downloadCoupon(id)
}