package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.data.ResponseTemplate
import retrofit2.Response

interface FavoriteRepository {
    suspend fun favoriteToggle(
        memberId: String,
        marketId: String,
    ) : Response<ResponseTemplate<Unit>>
}