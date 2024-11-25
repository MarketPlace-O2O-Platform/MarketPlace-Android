package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface FavoriteService {
    @POST("api/favorites")
    suspend fun favoriteToggle(
        @Query("memberId") memberId: String,
        @Query("marketId") marketId: String
    ) : Response<ResponseTemplate<Unit>>
}

interface User