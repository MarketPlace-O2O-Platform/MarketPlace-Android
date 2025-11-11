package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponsePageRequestMarketRes
import dev.kichan.marketplace.model.dto.CommonResponseRequestMarketRes
import dev.kichan.marketplace.model.dto.RequestMarketCreateReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RequestMarketsService {

    @GET("/api/request-markets")
    suspend fun createRequestMarket_1(@Query("page") page: Int? = null, @Query("size") size: Int? = null): Response<CommonResponsePageRequestMarketRes>

    @POST("/api/request-markets")
    suspend fun createRequestMarket(@Body body : RequestMarketCreateReq): Response<CommonResponseRequestMarketRes>

}