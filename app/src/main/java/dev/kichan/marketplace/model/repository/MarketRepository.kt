package dev.kichan.marketplace.model.repository

import LargeCategory
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.LatestCouponRes
import dev.kichan.marketplace.model.data.PageNationTemplate
import dev.kichan.marketplace.model.data.MarketPageNationRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.TopFavoriteMarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.TopClosingCouponRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.TopLatestCoupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.FavoriteMarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MyFavoriteMarketRes
import retrofit2.Response

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

    suspend fun getMarketSearch(
        name: String,
        lastPageIndex: Int?,
        pageSize: Int?,
    ) : Response<ResponseTemplate<MarketPageNationRes>>

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
}