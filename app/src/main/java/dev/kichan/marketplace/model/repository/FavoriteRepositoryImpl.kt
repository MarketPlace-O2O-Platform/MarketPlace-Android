package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.service.FavoriteService

class FavoriteRepositoryImpl : FavoriteRepository {
    private val favoriteService = NetworkModule.getService(FavoriteService::class.java)

    override suspend fun favoriteToggle(memberId: String, marketId: String) =
        favoriteService.favoriteToggle(
            memberId = memberId,
            marketId = marketId
        )
}