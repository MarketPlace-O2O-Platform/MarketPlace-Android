package dev.kichan.marketplace.model.services

import retrofit2.Response
import retrofit2.http.*
import dev.kichan.marketplace.model.dto.*

interface MarketsService {

    @GET("/api/markets")
    suspend fun getMarket(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("category") category: String? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseMarketPageResMarketRes>

    @GET("/api/markets/{marketId}")
    suspend fun getMarket_1(@Path("marketId") marketId: Long ): Response<CommonResponseMarketDetailsRes>

    @GET("/api/markets/search")
    suspend fun searchMarket_1(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("pageSize") pageSize: Int? = null, @Query("name") name: String? = null): Response<CommonResponseMarketPageResMarketRes>

    @GET("/api/markets/my-favorite")
    suspend fun getMemberFavoriteMarketList(@Query("lastModifiedAt") lastModifiedAt: String? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseMarketPageResMarketRes>

    @GET("/api/markets/map")
    suspend fun getMarketsByMap(
        @Query("lastPageIndex") lastPageIndex: Long? = null,
        @Query("category") category: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("address") address: String? = null
    ): Response<CommonResponseMarketPageResMarketRes>
}