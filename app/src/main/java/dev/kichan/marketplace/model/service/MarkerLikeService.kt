package dev.kichan.marketplace.model.service

import retrofit2.http.GET
import retrofit2.http.Query

interface MarkerLikeService {
    @GET("/api/tempMarkets")
    suspend fun getTempMarkets(
        @Query("memberId") memberId: Long,
        @Query("count") count: Long?
    )

    @GET("/api/tempMarkets/search")
    suspend fun getMarketSearch(
        @Query("memberId") memberId: Long,
        @Query("name") keyword: String,
    )

    @GET("/api/tempMarkets/cheer")
    suspend fun getCheerMarket(
        @Query("memberId") memberId: Long,
    )
}