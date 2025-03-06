package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.FavoritesService

class FavoriteRepository {
    private val service = NetworkModule.getService(FavoritesService::class.java)

    suspend fun favorite(memberId: Long, marketId: Long)
        = service.favorites(memberId, marketId)
}