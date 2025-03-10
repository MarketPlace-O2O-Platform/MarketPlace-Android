package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.FavoritesService

class FavoritesRepository {
    private val service = NetworkModule.getService(FavoritesService::class.java)

    suspend fun favorites(
        marketId: Long,
    ) = service.favorites(marketId)
}