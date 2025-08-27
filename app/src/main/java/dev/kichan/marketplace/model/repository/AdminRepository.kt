package dev.kichan.marketplace.model.repository

import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import dev.kichan.marketplace.model.services.AdminService
import dev.kichan.marketplace.model.dto.*

@Singleton
class AdminRepository @Inject constructor(
    private val service: AdminService
) {

    suspend fun getAllTempMarket(@Query("page") page: Int? = null, @Query("size") size: Int? = null): Response<CommonResponsePageTempMarketDetailRes> {
        return service.getAllTempMarket(page, size)
    }

    suspend fun updateTempMarket(@Query("marketId") marketId: Long? = null, @Part file : MultipartBody.Part, @Part("jsonData") jsonData : RequestBody): Response<CommonResponseTempMarketDetailRes> {
        return service.updateTempMarket(marketId, file, jsonData)
    }

    suspend fun createTempMarket(@Part file : MultipartBody.Part, @Part("jsonData") jsonData : RequestBody): Response<CommonResponseTempMarketDetailRes> {
        return service.createTempMarket(file, jsonData)
    }

    suspend fun toggleTempMarket(@Path("tempMarketId") tempMarketId: Long ): Response<CommonResponseTempMarketHiddenRes> {
        return service.toggleTempMarket(tempMarketId)
    }

    suspend fun updateCoupon_4(@Path("couponId") couponId: Long , @Body body : PaybackReq): Response<CommonResponsePaybackRes> {
        return service.updateCoupon_4(couponId, body)
    }

    suspend fun deleteCoupon_1(@Path("couponId") couponId: Long ): Response<CommonResponseObject> {
        return service.deleteCoupon_1(couponId)
    }

    suspend fun hiddenCoupon_1(@Path("couponId") couponId: Long ): Response<CommonResponseObject> {
        return service.hiddenCoupon_1(couponId)
    }

    suspend fun manageCoupon(@Path("couponId") couponId: Long ): Response<CommonResponseCouponHandleRes> {
        return service.manageCoupon(couponId)
    }

    suspend fun getCouponList_6(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResPaybackRes> {
        return service.getCouponList_6(marketId, couponId, size)
    }

    suspend fun createCoupon_2(@Query("marketId") marketId: Long? = null, @Body body : PaybackReq): Response<CommonResponsePaybackRes> {
        return service.createCoupon_2(marketId, body)
    }

    suspend fun getTempMarket_1(@Path("tempMarketId") tempMarketId: Long ): Response<CommonResponseTempMarketDetailRes> {
        return service.getTempMarket_1(tempMarketId)
    }

    suspend fun deleteMarket_1(@Path("tempMarketId") tempMarketId: Long ): Response<CommonResponseObject> {
        return service.deleteMarket_1(tempMarketId)
    }

}