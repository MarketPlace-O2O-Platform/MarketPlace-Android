import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*

interface CouponsService {

    @GET("/api/coupons")
    suspend fun getCouponList_4(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResCouponRes>

    @GET("/api/coupons/top/popular")
    suspend fun getPopularCoupon(@Query("pageSize") pageSize: Int? = null): Response<CommonResponseListTopPopularCouponRes>

    @GET("/api/coupons/top/latest")
    suspend fun getLatestCoupon(@Query("pageSize") pageSize: Int? = null): Response<CommonResponseListTopLatestCouponRes>

    @GET("/api/coupons/top/closing")
    suspend fun getClosingCoupon(@Query("pageSize") pageSize: Int? = null): Response<CommonResponseListTopClosingCouponRes>

    @GET("/api/coupons/popular")
    suspend fun getPopularCoupon_1(@Query("lastIssuedCount") lastIssuedCount: Long? = null, @Query("lastCouponId") lastCouponId: Long? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseCouponPageResCouponRes>

    @GET("/api/coupons/latest")
    suspend fun getLatestCoupon_1(@Query("lastCreatedAt") lastCreatedAt: String? = null, @Query("lastCouponId") lastCouponId: Long? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseCouponPageResCouponRes>

}