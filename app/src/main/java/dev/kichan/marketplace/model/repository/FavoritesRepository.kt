package dev.kichan.marketplace.model.repository

import retrofit2.Response
import retrofit2.http.*
import javax.inject.Inject
import javax.inject.Singleton
import dev.kichan.marketplace.model.services.FavoritesService
import dev.kichan.marketplace.model.dto.*

@Singleton
class FavoritesRepository @Inject constructor(
    private val service: FavoritesService
) {
    suspend fun favorite(@Query("marketId") marketId: Long? = null): Response<CommonResponseObject> {
        return service.favorite(marketId)
    }

    suspend fun unfavorite(@Query("marketId") marketId: Long? = null): Response<CommonResponseObject> {
        return service.unfavorite(marketId)
    }
}