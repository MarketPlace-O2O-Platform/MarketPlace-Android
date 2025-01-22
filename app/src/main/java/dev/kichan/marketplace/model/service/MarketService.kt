package dev.kichan.marketplace.model.service

import dev.kichan.marketplace.model.data.MarketPageNationRes
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.LatestCouponRes
import dev.kichan.marketplace.model.data.PageNationTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.TopFavoriteMarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.TopClosingCouponRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.TopLatestCoupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.FavoriteMarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MyFavoriteMarketRes
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
    ): Response<ResponseTemplate<PageNationTemplate<MarketRes>>>

    @GET("/api/markets/{marketId}")
    suspend fun getMarketDetail(
        @Path("marketId") marketId: String
    ): Response<ResponseTemplate<MarketDetailRes>>

    @GET("/api/markets/search")
    suspend fun getMarketSearch(
        @Query("name") name: String,
        @Query("lastPageIndex") lastPageIndex: Int?,
        @Query("pageSize") pageSize: Int?,
    ): Response<ResponseTemplate<MarketPageNationRes>>

    @GET("/api/markets/my-favorite")
    suspend fun getMyFavoriteMarkets(
        @Query("memberId") memberId: Int,
        @Query("lastModifiedAt") lastModifiedAt: String?,
        @Query("pageSize") pageSize: Int?
    ) : Response<ResponseTemplate<PageNationTemplate<MyFavoriteMarketRes>>>

    @GET("/api/markets/map")
    suspend fun getMarketsByAddress(
        @Query("memberId") memberId: Int,
        @Query("address") address: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int?,
    ) : Response<ResponseTemplate<PageNationTemplate<MarketRes>>>
}