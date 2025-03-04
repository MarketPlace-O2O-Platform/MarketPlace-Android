package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketCreateReq
import dev.kichan.marketplace.model.data.market.MarketUpdateReq
import dev.kichan.marketplace.model.service.MarketOwnerService
import okhttp3.MultipartBody

class MarketOwnerRepository {
    private val service: MarketOwnerService =
        NetworkModule.getService(MarketOwnerService::class.java)

    suspend fun getMarket(marketId: Int) = service.getMarket(marketId)
    suspend fun updateMarket(marketId: Int, body: MarketUpdateReq) = service.updateMarket(marketId, body)
    suspend fun deleteMarket(marketId: Int) = service.deleteMarket(marketId)
    suspend fun createMarket(body: MarketCreateReq, images: List<MultipartBody.Part>) =
        service.createMarket(body, images)
}