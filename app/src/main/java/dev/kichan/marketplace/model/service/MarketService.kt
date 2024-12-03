package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketPageRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketService {
    /**
     * 전체 매장 조회
     * */
    @GET("/api/markets")
    suspend fun getMarkets(
        @Query("lastPageIndex") lastPageIndex: Int,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
    ): Response<ResponseTemplate<MarketPageRes>>

    /**
     * 매장 상세 조회
     * */
    @GET("/api/markets/{marketId}")
    suspend fun getMarketDetail(
        @Path("marketId") marketId: Int
    ): Response<ResponseTemplate<MarketDetailRes>>

    /**
     * 찜수가 가장 많은 매장
     * */
    @GET("/api/markets/top-favorite-markets")
    suspend fun getTopFavoriteMarkets(
        @Query("lastPageIndex") lastPageIndex: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>>

    /**
     * 자신이 찜한 매장
     * */
    @GET("/api/markets/my-favorite-markets")
    suspend fun getMyFavoriteMarkets(
        @Query("memberId") memberId: Int,
        @Query("lastPageIndex") lastPageIndex: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseTemplate<MarketPageRes>>

    /**
     * 마감 임박 쿠폰
     * */
    @GET("/api/markets/top-closing-coupon")
    suspend fun getTopClosingCoupon(
        @Query("lastPageIndex") count: Int,
    )
}

