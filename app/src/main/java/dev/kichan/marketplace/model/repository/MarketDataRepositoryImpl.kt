package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketPageRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service.MarketDataService
import retrofit2.Response

class MarketDataRepositoryImpl : MarketDataRepository {
    private val marketDataRepository = NetworkModule.getService(MarketDataService::class.java)

    override suspend fun getMarkets(
        lastPageIndex: Int,
        category: LargeCategory,
        pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>> =
        marketDataRepository.getMarkets(lastPageIndex, category.backendLable, pageSize)

    override suspend fun getMarketDetail(marketId: Int): Response<ResponseTemplate<MarketDetailRes>> =
        marketDataRepository.getMarketDetail(marketId = marketId)

    override suspend fun getTopFavoriteMarkets(
        lastPageIndex: Int,
        pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>> = marketDataRepository.getTopFavoriteMarkets(
        lastPageIndex = lastPageIndex,
        pageSize = pageSize
    )

    override suspend fun getMyFavoriteMarkets(
        memberId: Int,
        lastPageIndex: Int,
        pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>> = marketDataRepository.getMyFavoriteMarkets(
        memberId = memberId,
        lastPageIndex = lastPageIndex,
        pageSize = pageSize
    )
}