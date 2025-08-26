import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import PaybackCouponsService

@Singleton
class PaybackCouponsRepository @Inject constructor(
    private val service: PaybackCouponsService
) {

    suspend fun getCouponList(@Query("marketId") marketId: Long? = null, @Query("couponId") couponId: Long? = null, @Query("size") size: Int? = null): Response<CommonResponseCouponPageResPaybackRes> {
        return service.getCouponList(marketId, couponId, size)
    }

}