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
        @Query("memberId") memberId: Int,
        @Query("lastPageIndex") lastPageIndex: Int,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
    ): Response<ResponseTemplate<MarketPageRes>>

    @GET("/api/markets/{marketId}")
    suspend fun getMarketDetail(
        @Path("marketId") marketId: Int
    ): Response<ResponseTemplate<MarketDetailRes>>

    @GET("/api/markets/top-latest-coupons")
    suspend fun getTopLatestCoupons(
        @Query("memberId") memberId: Int,
        @Query("count") pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>>

    @GET("/api/markets/top-favorite-markets")
    suspend fun getTopFavoriteMarkets(
        @Query("memberId") memberId: Int,
        @Query("count") pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>>

    @GET("/api/markets/my-favorite-markets")
    suspend fun getMyFavoriteMarkets(
        @Query("memberId") memberId: Int,
        @Query("pageSize") pageSize: Int?
    ): Response<ResponseTemplate<MarketPageRes>>

    @GET("/api/markets/top-latest-coupon")
    suspend fun getTopLatestCoupon(
        @Query("memberId") memberId: Int,
        @Query("count") count: Int
    ) : Response<ResponseTemplate<MarketPageRes>>
}

