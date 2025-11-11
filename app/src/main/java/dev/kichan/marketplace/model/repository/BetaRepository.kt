package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponseBetaCouponPageResBetaCouponRes
import dev.kichan.marketplace.model.dto.CommonResponseBetaMarketRes
import dev.kichan.marketplace.model.dto.CommonResponseObject
import dev.kichan.marketplace.model.services.BetaService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BetaRepository @Inject constructor(
    private val service: BetaService
) {

    suspend fun updateMarket_2(@Path("betaMarketId") betaMarketId: Long , @Part("jsonData") jsonData : RequestBody, @Part image : MultipartBody.Part): Response<CommonResponseBetaMarketRes> {
        return service.updateMarket_2(betaMarketId, jsonData, image)
    }

    suspend fun updateCoupon_3(@Path("betaCouponId") betaCouponId: Long ): Response<CommonResponseObject> {
        return service.updateCoupon_3(betaCouponId)
    }

    suspend fun createMarket_1(@Part("jsonData") jsonData : RequestBody, @Part image : MultipartBody.Part): Response<CommonResponseBetaMarketRes> {
        return service.createMarket_1(jsonData, image)
    }

    suspend fun getCouponList_5(@Query("betaCouponId") betaCouponId: Long? = null, @Query("category") category: String? = null, @Query("size") size: Int? = null): Response<CommonResponseBetaCouponPageResBetaCouponRes> {
        return service.getCouponList_5(betaCouponId, category, size)
    }

}