package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponseObject
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface CheerService {

    @POST("/api/cheer")
    suspend fun createCheer(@Query("tempMarketId") tempMarketId: Long? = null): Response<CommonResponseObject>

}