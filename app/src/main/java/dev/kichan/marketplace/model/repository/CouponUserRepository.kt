package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.CouponMember
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service.CouponUserService
import retrofit2.Response

interface CouponUserRepository {
    suspend fun getCouponList(
        marketId: String
    ): Response<ResponseTemplate<List<CouponMember>>>

    suspend fun getLstestCoupon(
        lastPageIndex: Int,
        lastModified: String,
        pageSize: Int
    ): Response<ResponseTemplate<List<CouponMember>>>

    suspend fun getLatestTopCoupon(
        count: Int
    ): Response<ResponseTemplate<List<CouponMember>>>

    suspend fun getClosingCoupon(
        count: Int
    ): Response<ResponseTemplate<List<CouponMember>>>
}

class CouponUserRepositoryImpl : CouponUserRepository {
    private val service = NetworkModule.getService(CouponUserService::class.java)

    override suspend fun getCouponList(marketId: String): Response<ResponseTemplate<List<CouponMember>>> =
        service.getCouponList(marketId)

    override suspend fun getLstestCoupon(
        lastPageIndex: Int,
        lastModified: String,
        pageSize: Int
    ): Response<ResponseTemplate<List<CouponMember>>> =
        service.getLatestCoupon(lastPageIndex, lastModified, pageSize)

    override suspend fun getLatestTopCoupon(count: Int): Response<ResponseTemplate<List<CouponMember>>> =
        service.getLatestTopCoupon(count)

    override suspend fun getClosingCoupon(count: Int): Response<ResponseTemplate<List<CouponMember>>> =
        service.getClosingCoupon(count)
}