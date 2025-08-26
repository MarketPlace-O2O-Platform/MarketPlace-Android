import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import OwnersService

@Singleton
class OwnersRepository @Inject constructor(
    private val service: OwnersService
) {

    suspend fun updateMarket(@Path("marketId") marketId: Long , @Body body : MarketReq): Response<CommonResponseMarketDetailsRes> {
        return service.updateMarket(marketId, body)
    }

    suspend fun deleteMarket(@Path("marketId") marketId: Long ): Response<CommonResponseObject> {
        return service.deleteMarket(marketId)
    }

    suspend fun updateMarket_1(@Query("marketId") marketId: Long? = null, @Part("jsonData") jsonData : RequestBody, @Part images : List<MultipartBody.Part>): Response<CommonResponseMarketDetailsRes> {
        return service.updateMarket_1(marketId, jsonData, images)
    }

    suspend fun getCoupon(@Path("couponId") couponId: Long ): Response<CommonResponseCouponRes> {
        return service.getCoupon(couponId)
    }

    suspend fun updateCoupon(@Path("couponId") couponId: Long , @Body body : CouponReq): Response<CommonResponseCouponRes> {
        return service.updateCoupon(couponId, body)
    }

    suspend fun deleteCoupon(@Path("couponId") couponId: Long ): Response<CommonResponseObject> {
        return service.deleteCoupon(couponId)
    }

    suspend fun hiddenCoupon(@Path("couponId") couponId: Long ): Response<CommonResponseObject> {
        return service.hiddenCoupon(couponId)
    }

    suspend fun createMarket(@Part("jsonData") jsonData : RequestBody, @Part images : List<MultipartBody.Part>): Response<CommonResponseMarketDetailsRes> {
        return service.createMarket(jsonData, images)
    }

    suspend fun getCouponList_1(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResCouponRes> {
        return service.getCouponList_1(marketId, couponId, size)
    }

    suspend fun createCoupon(@Query("marketId") marketId: Long? = null, @Body body : CouponReq): Response<CommonResponseCouponRes> {
        return service.createCoupon(marketId, body)
    }

}