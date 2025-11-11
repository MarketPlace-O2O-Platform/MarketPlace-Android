package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponseTempMarketPageResTempMarketRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TempmarketsService {

    @GET("/api/tempMarkets")
    suspend fun getTempMarket(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("category") category: String? = null, @Query("count") count: Int? = null): Response<CommonResponseTempMarketPageResTempMarketRes>

    @GET("/api/tempMarkets/search")
    suspend fun searchMarket(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("pageSize") pageSize: Int? = null, @Query("name") name: String? = null): Response<CommonResponseTempMarketPageResTempMarketRes>

    @GET("/api/tempMarkets/cheer")
    suspend fun getTempMarketPage(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("lastCheerCount") lastCheerCount: Int? = null, @Query("count") count: Int? = null): Response<CommonResponseTempMarketPageResTempMarketRes>

}