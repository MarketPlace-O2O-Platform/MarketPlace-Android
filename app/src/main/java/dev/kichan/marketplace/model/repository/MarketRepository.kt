package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.MarketService

class MarketRepository {
    private val service: MarketService = NetworkModule.getService(MarketService::class.java)

    suspend fun getMarkets(memberId: Long, lastMarketId: String?, category: String?, pageSize: Int?) =
        service.getMarkets(memberId, lastMarketId, category, pageSize)

    suspend fun getMarket(marketId: Long) = service.getMarket(marketId)
    suspend fun searchMarket(name: String, lastMarketId: Long?, pageSize: Int?) =
        service.searchMarket(name, lastMarketId, pageSize)
    suspend fun getFavoriteMarket(memberId: Long, lastMarketId: String?, pageSize: Int?) =
        service.getFavoriteMarket(memberId, lastMarketId, pageSize)
}