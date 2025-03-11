package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.MarketService

class MarketRepository {
    private val service: MarketService = NetworkModule.getService(MarketService::class.java)

    suspend fun getMarkets(lastMarketId: String?, category: String?, pageSize: Int?) =
        service.getMarkets(lastMarketId, category, pageSize)

    suspend fun getMarket(marketId: Long) = service.getMarket(marketId)
    suspend fun searchMarket(name: String, lastMarketId: Long?, pageSize: Int?) =
        service.searchMarket(name, lastMarketId, pageSize)
    suspend fun getFavoriteMarket(lastMarketId: String?, pageSize: Int?) =
        service.getFavoriteMarket(lastMarketId, pageSize)
}