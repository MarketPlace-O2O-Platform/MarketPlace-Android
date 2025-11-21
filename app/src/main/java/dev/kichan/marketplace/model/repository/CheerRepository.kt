package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponseCheerCountRes
import dev.kichan.marketplace.model.services.CheerService
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheerRepository @Inject constructor(
    private val service: CheerService
) {

    suspend fun createCheer(@Query("tempMarketId") tempMarketId: Long? = null): Response<CommonResponseCheerCountRes> {
        return service.createCheer(tempMarketId)
    }

}