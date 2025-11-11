package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponsePageRequestMarketRes
import dev.kichan.marketplace.model.dto.CommonResponseRequestMarketRes
import dev.kichan.marketplace.model.dto.RequestMarketCreateReq
import dev.kichan.marketplace.model.services.RequestMarketsService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestMarketsRepository @Inject constructor(
    private val service: RequestMarketsService
) {

    suspend fun createRequestMarket_1(@Query("page") page: Int? = null, @Query("size") size: Int? = null): Response<CommonResponsePageRequestMarketRes> {
        return service.createRequestMarket_1(page, size)
    }

    suspend fun createRequestMarket(@Body body : RequestMarketCreateReq): Response<CommonResponseRequestMarketRes> {
        return service.createRequestMarket(body)
    }

}