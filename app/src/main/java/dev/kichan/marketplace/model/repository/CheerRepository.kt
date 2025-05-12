package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.service.CheerService
import dev.kichan.marketplace.model.NetworkModule

class CheerRepository {
    val service = NetworkModule.getService(CheerService::class.java)

    suspend fun cheer(
        marketId: Long
    ) = service.cheer(marketId)
}