package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponseTempMarketPageResTempMarketRes
import dev.kichan.marketplace.model.services.TempmarketsService
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TempmarketsRepository @Inject constructor(
    private val service: TempmarketsService
) {

    suspend fun getTempMarket(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("category") category: String? = null, @Query("count") count: Int? = null): Response<CommonResponseTempMarketPageResTempMarketRes> {
        return service.getTempMarket(lastPageIndex, category, count)
    }

    suspend fun searchMarket(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("pageSize") pageSize: Int? = null, @Query("name") name: String? = null): Response<CommonResponseTempMarketPageResTempMarketRes> {
        return service.searchMarket(lastPageIndex, pageSize, name)
    }

    suspend fun getTempMarketPage(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("lastCheerCount") lastCheerCount: Int? = null, @Query("count") count: Int? = null): Response<CommonResponseTempMarketPageResTempMarketRes> {
        return service.getTempMarketPage(lastPageIndex, lastCheerCount, count)
    }

}