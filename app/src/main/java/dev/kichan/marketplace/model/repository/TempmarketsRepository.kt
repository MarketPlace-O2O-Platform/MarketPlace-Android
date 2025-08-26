package dev.kichan.marketplace.model.repository

import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import dev.kichan.marketplace.model.services.TempmarketsService
import dev.kichan.marketplace.model.dto.*

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