package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponseObject
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface FavoritesService {

    @POST("/api/favorites")
    suspend fun favorite(@Query("marketId") marketId: Long? = null): Response<CommonResponseObject>

    @DELETE("/api/favorites")
    suspend fun unfavorite(@Query("marketId") marketId: Long? = null): Response<CommonResponseObject>

}