package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponseCheerCountRes
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface CheerService {

    @POST("/api/cheer")
    suspend fun createCheer(@Query("tempMarketId") tempMarketId: Long? = null): Response<CommonResponseCheerCountRes>

}