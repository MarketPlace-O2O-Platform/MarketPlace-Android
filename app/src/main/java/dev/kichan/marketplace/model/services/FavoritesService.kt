package dev.kichan.marketplace.model.services

import retrofit2.Response
import retrofit2.http.*
import dev.kichan.marketplace.model.dto.*

interface FavoritesService {

    @POST("/api/favorites")
    suspend fun favorite(@Query("marketId") marketId: Long? = null): Response<CommonResponseObject>

    @DELETE("/api/favorites")
    suspend fun unfavorite(@Query("marketId") marketId: Long? = null): Response<CommonResponseObject>

}