package dev.kichan.marketplace.model.repository

import retrofit2.Response
import retrofit2.http.*
import javax.inject.Inject
import javax.inject.Singleton
import dev.kichan.marketplace.model.services.MarketsService
import dev.kichan.marketplace.model.dto.*

@Singleton
class MarketsRepository @Inject constructor(
    private val service: MarketsService
) {

    suspend fun getMarket(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("category") category: String? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseMarketPageResMarketRes> {
        return service.getMarket(lastPageIndex, category, pageSize)
    }

    suspend fun getMarket_1(@Path("marketId") marketId: Long ): Response<CommonResponseMarketDetailsRes> {
        return service.getMarket_1(marketId)
    }

    suspend fun searchMarket_1(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("pageSize") pageSize: Int? = null, @Query("name") name: String? = null): Response<CommonResponseMarketPageResMarketRes> {
        return service.searchMarket_1(lastPageIndex, pageSize, name)
    }

    suspend fun getMemberFavoriteMarketList(@Query("lastModifiedAt") lastModifiedAt: String? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseMarketPageResMarketRes> {
        return service.getMemberFavoriteMarketList(lastModifiedAt, pageSize)
    }

    suspend fun getMarketsByMap(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("category") category: String? = null, @Query("pageSize") pageSize: Int? = null, @Query("address") address: String? = null): Response<CommonResponseMarketPageResMarketRes> {
        return service.getMarketsByMap(lastPageIndex, category, pageSize, address)
    }
}