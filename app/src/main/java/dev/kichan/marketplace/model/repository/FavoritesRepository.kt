package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponseObject
import dev.kichan.marketplace.model.services.FavoritesService
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

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