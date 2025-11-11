package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponseBetaCouponPageResBetaCouponRes
import dev.kichan.marketplace.model.dto.CommonResponseBetaMarketRes
import dev.kichan.marketplace.model.dto.CommonResponseObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface BetaService {

    @PUT("/api/beta/markets/{betaMarketId}")
    suspend fun updateMarket_2(@Path("betaMarketId") betaMarketId: Long , @Part("jsonData") jsonData : RequestBody, @Part image : MultipartBody.Part): Response<CommonResponseBetaMarketRes>

    @PUT("/api/beta/coupons/{betaCouponId}")
    suspend fun updateCoupon_3(@Path("betaCouponId") betaCouponId: Long ): Response<CommonResponseObject>

    @POST("/api/beta/markets")
    suspend fun createMarket_1(@Part("jsonData") jsonData : RequestBody, @Part image : MultipartBody.Part): Response<CommonResponseBetaMarketRes>

    @GET("/api/beta/coupons")
    suspend fun getCouponList_5(@Query("betaCouponId") betaCouponId: Long? = null, @Query("category") category: String? = null, @Query("size") size: Int? = null): Response<CommonResponseBetaCouponPageResBetaCouponRes>

}