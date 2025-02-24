package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.MarkerLikeService

class MarkerLikeRepository {
    private val service: MarkerLikeService = NetworkModule.getService(MarkerLikeService::class.java)

    suspend fun getTempMarkets(memberId: Long, count: Long?) = service.getTempMarkets(memberId, count)

    suspend fun getMarketSearch(memberId: Long, keyword: String) = service.getMarketSearch(memberId, keyword)

    suspend fun getCheerMarket(memberId: Long) = service.getCheerMarket(memberId)
}