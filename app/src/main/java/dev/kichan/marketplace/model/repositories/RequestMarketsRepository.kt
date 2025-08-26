import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import RequestMarketsService

@Singleton
class RequestMarketsRepository @Inject constructor(
    private val service: RequestMarketsService
) {

    suspend fun createRequestMarket_1(@Query("page") page: Int? = null, @Query("size") size: Int? = null): Response<CommonResponsePageRequestMarketRes> {
        return service.createRequestMarket_1(page, size)
    }

    suspend fun createRequestMarket(@Body body : RequestMarketCreateReq): Response<CommonResponseRequestMarketRes> {
        return service.createRequestMarket(body)
    }

}