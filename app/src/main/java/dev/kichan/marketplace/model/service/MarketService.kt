package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketPageRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketService {
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

    @GET("/api/markets/latest-coupon")
    suspend fun getMyLatestCoupon(
        @Query("memberId") memberId: Int,
        @Query("lastPageIndex") lastPageIndex: Int? = null,
        @Query("lastCreatedAt") lastCreatedAt: String? = null,
        @Query("pageSize") pageSize: Int? = null
    ) : Response<ResponseTemplate<MarketPageRes>>
}

