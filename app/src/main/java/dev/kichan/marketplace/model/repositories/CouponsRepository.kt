import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import CouponsService

@Singleton
class CouponsRepository @Inject constructor(
    private val service: CouponsService
) {

    suspend fun getCouponList_4(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResCouponRes> {
        return service.getCouponList_4(marketId, couponId, size)
    }

    suspend fun getPopularCoupon(@Query("pageSize") pageSize: Int? = null): Response<CommonResponseListTopPopularCouponRes> {
        return service.getPopularCoupon(pageSize)
    }

    suspend fun getLatestCoupon(@Query("pageSize") pageSize: Int? = null): Response<CommonResponseListTopLatestCouponRes> {
        return service.getLatestCoupon(pageSize)
    }

    suspend fun getClosingCoupon(@Query("pageSize") pageSize: Int? = null): Response<CommonResponseListTopClosingCouponRes> {
        return service.getClosingCoupon(pageSize)
    }

    suspend fun getPopularCoupon_1(@Query("lastIssuedCount") lastIssuedCount: Long? = null, @Query("lastCouponId") lastCouponId: Long? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseCouponPageResCouponRes> {
        return service.getPopularCoupon_1(lastIssuedCount, lastCouponId, pageSize)
    }

    suspend fun getLatestCoupon_1(@Query("lastCreatedAt") lastCreatedAt: String? = null, @Query("lastCouponId") lastCouponId: Long? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseCouponPageResCouponRes> {
        return service.getLatestCoupon_1(lastCreatedAt, lastCouponId, pageSize)
    }

}