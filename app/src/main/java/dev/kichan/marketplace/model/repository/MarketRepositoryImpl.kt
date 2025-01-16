package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.repository

import LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.ResponseTemplate
import dev.kichan.marketplace.model.data.coupon.LatestCouponRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.PageNationTemplate
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.TopFavoriteMarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.TopClosingCouponRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon.TopLatestCoupon
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.FavoriteMarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market.MyFavoriteMarketRes
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.service.MarketService
import retrofit2.Response

class MarketRepositoryImpl : MarketRepository {
    private val marketService = NetworkModule.getService(MarketService::class.java)

    override suspend fun getMarkets(
        memberId: Int,
        lastPageIndex: Int,
        category: String,
        pageSize: Int
    ): Response<ResponseTemplate<PageNationTemplate<MarketRes>>> =
        marketService.getMarkets(memberId, lastPageIndex, category, pageSize)

    override suspend fun getMarketDetail(marketId: String): Response<ResponseTemplate<MarketDetailRes>> {
        return marketService.getMarketDetail(marketId = marketId)
    }

    override suspend fun getTopLatestCoupons(
        memberId: Int,
        pageSize: Int?
    ): Response<ResponseTemplate<List<TopLatestCoupon>>> {
        return marketService.getTopLatestCoupons(memberId, pageSize)
    }

    override suspend fun getTopFavoriteMarkets(
        memberId: Int,
        pageSize: Int?
    ): Response<ResponseTemplate<TopFavoriteMarketRes>> {
        return marketService.getTopFavoriteMarkets(memberId, pageSize)
    }

    override suspend fun getTopClosingCoupon(pageSize: Int?): Response<ResponseTemplate<List<TopClosingCouponRes>>> {
        return marketService.getTopClosingCoupon(pageSize)
    }

    override suspend fun getMyFavoriteMarkets(
        memberId: Int,
        lastModifiedAt: String?,
        pageSize: Int?
    ): Response<ResponseTemplate<PageNationTemplate<MyFavoriteMarketRes>>> {
        return marketService.getMyFavoriteMarkets(memberId, lastModifiedAt, pageSize)
    }

    override suspend fun getMarketsByAddress(
        memberId: Int,
        address: String,
        category: LargeCategory,
        pageSize: Int?
    ): Response<ResponseTemplate<PageNationTemplate<MarketRes>>> {
        return marketService.getMarketsByAddress(memberId, address, category.backendLable, pageSize)
    }

    override suspend fun getLatestCoupon(
        memberId: Int,
        pageSize: Int?
    ): Response<ResponseTemplate<PageNationTemplate<LatestCouponRes>>> {
        return marketService.getLatestCoupon(memberId, pageSize)
    }

    override suspend fun getMarketsByFavorite(
        memberId: Int,
        pageSize: Int?
    ): Response<ResponseTemplate<PageNationTemplate<FavoriteMarketRes>>> {
        return marketService.getMarketsByFavorite(memberId, pageSize)
    }
}