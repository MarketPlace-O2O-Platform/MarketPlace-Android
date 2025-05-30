package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.MarkerLikeMemberService

class MarkerLikeRepository {
    private val service: MarkerLikeMemberService = NetworkModule.getService(MarkerLikeMemberService::class.java)

    suspend fun getTempMarkets(count: Long?, category: LargeCategory) = service.getTempMarkets(count, if(category == LargeCategory.All) null else category.backendLabel)

    suspend fun getMarketSearch(keyword: String) = service.getMarketSearch(keyword)

    suspend fun getCheerMarket() = service.getCheerMarket()
}