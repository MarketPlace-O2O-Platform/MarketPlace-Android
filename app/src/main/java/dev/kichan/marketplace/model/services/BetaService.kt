package dev.kichan.marketplace.model.services

import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import dev.kichan.marketplace.model.dto.*

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