package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import LargeCategory
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketPageRes
import retrofit2.Response

interface MarketRepository {
    suspend fun getMarkets(
        lastPageIndex: Int,
        category: LargeCategory,
        pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>>

    suspend fun getMarketDetail(
        marketId: Int
    ): Response<ResponseTemplate<MarketDetailRes>>

    suspend fun getTopFavoriteMarkets(
        memberId: Int,
        pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>>

    suspend fun getMyFavoriteMarkets(
        memberId: Int,
        lastPageIndex: Int,
        pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>>

    suspend fun getLatestCoupon(
        memberId: Int,
        count: Int,
    ) : Response<ResponseTemplate<MarketPageRes>>
}