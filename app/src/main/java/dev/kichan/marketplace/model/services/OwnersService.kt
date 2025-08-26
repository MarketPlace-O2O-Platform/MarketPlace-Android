import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*

interface OwnersService {

    @PUT("/api/owners/markets/{marketId}")
    suspend fun updateMarket(@Path("marketId") marketId: Long , @Body body : MarketReq): Response<CommonResponseMarketDetailsRes>

    @DELETE("/api/owners/markets/{marketId}")
    suspend fun deleteMarket(@Path("marketId") marketId: Long ): Response<CommonResponseObject>

    @PUT("/api/owners/markets/images")
    suspend fun updateMarket_1(@Query("marketId") marketId: Long? = null, @Part("jsonData")jsonData : RequestBody, @Part images : List<MultipartBody.Part>): Response<CommonResponseMarketDetailsRes>

    @GET("/api/owners/coupons/{couponId}")
    suspend fun getCoupon(@Path("couponId") couponId: Long ): Response<CommonResponseCouponRes>

    @PUT("/api/owners/coupons/{couponId}")
    suspend fun updateCoupon(@Path("couponId") couponId: Long , @Body body : CouponReq): Response<CommonResponseCouponRes>

    @DELETE("/api/owners/coupons/{couponId}")
    suspend fun deleteCoupon(@Path("couponId") couponId: Long ): Response<CommonResponseObject>

    @PUT("/api/owners/coupons/hidden/{couponId}")
    suspend fun hiddenCoupon(@Path("couponId") couponId: Long ): Response<CommonResponseObject>

    @POST("/api/owners/markets")
    suspend fun createMarket(@Part("jsonData")jsonData : RequestBody, @Part images : List<MultipartBody.Part>): Response<CommonResponseMarketDetailsRes>

    @GET("/api/owners/coupons")
    suspend fun getCouponList_1(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResCouponRes>

    @POST("/api/owners/coupons")
    suspend fun createCoupon(@Query("marketId") marketId: Long? = null, @Body body : CouponReq): Response<CommonResponseCouponRes>

}