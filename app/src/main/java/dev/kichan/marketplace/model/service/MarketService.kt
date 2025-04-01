package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.MarketPageNationRes
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.model.data.market.MarketRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketService {
    @GET("/api/markets")
    suspend fun getMarkets(
        @Query("lastPageIndex") lastMarketId: String?,
        @Query("category") category: String?,
        @Query("pageSize") pageSize : Int?,
    ) : Response<ResponseTemplate<MarketPageNationRes<MarketRes>>>

    @GET("/api/markets/{marketId}")
    suspend fun getMarket(
        @Path("marketId") marketId: Long
    ) : Response<ResponseTemplate<MarketDetailRes>>

    @GET("/api/markets/search")
    suspend fun searchMarket(
        @Query("name") key : String,
        @Query("lastPageIndex") lastMarketId : Long?,
        @Query("pageSize") pageSize : Int?,
    ) : Response<ResponseTemplate<MarketPageNationRes<MarketRes>>>

    @GET("/api/markets/my-favorite")
    suspend fun getFavoriteMarket(
        @Query("lastPageIndex") lastMarketId: String?,
        @Query("pageSize") pageSize : Int?,
    ): Response<ResponseTemplate<MarketPageNationRes<MarketRes>>>

    @GET("/api/markets/map")
    suspend fun getMarketByAddress(
        @Query("address") address: String,
        @Query("lastPageIndex") lastMarketId: String?,
        @Query("category") category: String?,
        @Query("pageSize") pageSize : Int?,
    ): Response<ResponseTemplate<MarketPageNationRes<MarketRes>>>
}