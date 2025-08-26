import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*

interface AdminService {

    @GET("/api/admin/temp-markets")
    suspend fun getAllTempMarket(@Query("page") page: Int? = null, @Query("size") size: Int? = null): Response<CommonResponsePageTempMarketDetailRes>

    @PUT("/api/admin/temp-markets")
    suspend fun updateTempMarket(@Query("marketId") marketId: Long? = null, @Part file : MultipartBody.Part, @Part("jsonData")jsonData : RequestBody): Response<CommonResponseTempMarketDetailRes>

    @POST("/api/admin/temp-markets")
    suspend fun createTempMarket(@Part file : MultipartBody.Part, @Part("jsonData")jsonData : RequestBody): Response<CommonResponseTempMarketDetailRes>

    @PUT("/api/admin/temp-markets/hidden/{tempMarketId}")
    suspend fun toggleTempMarket(@Path("tempMarketId") tempMarketId: Long ): Response<CommonResponseTempMarketHiddenRes>

    @PUT("/api/admin/payback-coupons/{couponId}")
    suspend fun updateCoupon_4(@Path("couponId") couponId: Long , @Body body : PaybackReq): Response<CommonResponsePaybackRes>

    @DELETE("/api/admin/payback-coupons/{couponId}")
    suspend fun deleteCoupon_1(@Path("couponId") couponId: Long ): Response<CommonResponseObject>

    @PUT("/api/admin/payback-coupons/hidden/{couponId}")
    suspend fun hiddenCoupon_1(@Path("couponId") couponId: Long ): Response<CommonResponseObject>

    @PUT("/api/admin/payback-coupons/complete/{couponId}")
    suspend fun manageCoupon(@Path("couponId") couponId: Long ): Response<CommonResponseCouponHandleRes>

    @GET("/api/admin/payback-coupons")
    suspend fun getCouponList_6(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResPaybackRes>

    @POST("/api/admin/payback-coupons")
    suspend fun createCoupon_2(@Query("marketId") marketId: Long? = null, @Body body : PaybackReq): Response<CommonResponsePaybackRes>

    @GET("/api/admin/temp-markets/{tempMarketId}")
    suspend fun getTempMarket_1(@Path("tempMarketId") tempMarketId: Long ): Response<CommonResponseTempMarketDetailRes>

    @DELETE("/api/admin/temp-markets/{tempMarketId}")
    suspend fun deleteMarket_1(@Path("tempMarketId") tempMarketId: Long ): Response<CommonResponseObject>

}