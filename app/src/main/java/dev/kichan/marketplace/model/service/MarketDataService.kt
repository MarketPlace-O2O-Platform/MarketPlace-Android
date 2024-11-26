package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketPageRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//todo: MarketService로 이름 변경
interface MarketDataService {
    @GET("/api/markets")
    suspend fun getMarkets(
        @Query("lastPageIndex") lastPageIndex: Int,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
    ): Response<ResponseTemplate<MarketPageRes>>

    @GET("/api/markets/{marketId}")
    suspend fun getMarketDetail(
        @Path("marketId") marketId: Int
    ): Response<ResponseTemplate<MarketDetailRes>>

    @GET("/api/markets/top-favorite-markets")
    suspend fun getTopFavoriteMarkets(
        @Query("lastPageIndex") lastPageIndex: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>>

    @GET("/api/markets/my-favorite-markets")
    suspend fun getMyFavoriteMarkets(
        @Query("memberId") memberId: Int,
        @Query("lastPageIndex") lastPageIndex: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>>
}

