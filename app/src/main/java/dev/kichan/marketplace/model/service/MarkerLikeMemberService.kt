package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.MarketPageNationRes
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.like.TempMarketRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarkerLikeMemberService {
    @GET("/api/tempMarkets")
    suspend fun getTempMarkets(
        @Query("memberId") memberId: Long,
        @Query("count") count: Long?
    ): Response<ResponseTemplate<MarketPageNationRes<TempMarketRes>>>

    @GET("/api/tempMarkets/search")
    suspend fun getMarketSearch(
        @Query("memberId") memberId: Long,
        @Query("name") keyword: String,
    ): Response<ResponseTemplate<MarketPageNationRes<TempMarketRes>>>

    @GET("/api/tempMarkets/cheer")
    suspend fun getCheerMarket(
        @Query("memberId") memberId: Long,
    ): Response<ResponseTemplate<MarketPageNationRes<TempMarketRes>>>
}