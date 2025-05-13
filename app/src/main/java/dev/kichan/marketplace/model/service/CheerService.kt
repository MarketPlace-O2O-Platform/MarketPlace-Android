package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface CheerService {
    @POST("/api/cheer")
    suspend fun cheer(
        @Query("tempMarketId") marketId: Long,
    ): Response<ResponseTemplate<Unit>>
}