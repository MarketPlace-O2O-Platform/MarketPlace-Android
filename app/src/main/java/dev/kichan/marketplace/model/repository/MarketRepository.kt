package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import LargeCategory
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.LatestCouponRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.PageNationTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.TopFavoriteMarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.TopClosingCouponRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.TopLatestCoupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.FavoriteMarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketPageRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MyFavoriteMarketRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketRepository {
    suspend fun getMarkets(
        memberId: Int,
        lastPageIndex: Int,
        category: String,
        pageSize: Int,
    ): Response<ResponseTemplate<PageNationTemplate<MarketRes>>>

    suspend fun getMarketDetail(
        marketId: String
    ): Response<ResponseTemplate<MarketDetailRes>>

    suspend fun getTopLatestCoupons(
        memberId: Int,
        pageSize: Int?
    ): Response<ResponseTemplate<List<TopLatestCoupon>>>

    suspend fun getTopFavoriteMarkets(
        memberId: Int,
        pageSize: Int?
    ): Response<ResponseTemplate<TopFavoriteMarketRes>>

    suspend fun getTopClosingCoupon(
        pageSize: Int?
    ): Response<ResponseTemplate<List<TopClosingCouponRes>>>

    suspend fun getMyFavoriteMarkets(
        memberId: Int,
        lastModifiedAt: String?,
        pageSize: Int?
    ): Response<ResponseTemplate<PageNationTemplate<MyFavoriteMarketRes>>>

    suspend fun getMarketsByAddress(
        memberId: Int,
        address: String,
        category: LargeCategory,
        pageSize: Int?,
    ): Response<ResponseTemplate<PageNationTemplate<MarketRes>>>

    suspend fun getLatestCoupon(
        memberId: Int,
        pageSize: Int?,
    ): Response<ResponseTemplate<PageNationTemplate<LatestCouponRes>>>

    suspend fun getMarketsByFavorite(
        memberId: Int,
        pageSize: Int?,
    ): Response<ResponseTemplate<PageNationTemplate<FavoriteMarketRes>>>
}