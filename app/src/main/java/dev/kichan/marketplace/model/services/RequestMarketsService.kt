import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*

interface RequestMarketsService {

    @GET("/api/request-markets")
    suspend fun createRequestMarket_1(@Query("page") page: Int? = null, @Query("size") size: Int? = null): Response<CommonResponsePageRequestMarketRes>

    @POST("/api/request-markets")
    suspend fun createRequestMarket(@Body body : RequestMarketCreateReq): Response<CommonResponseRequestMarketRes>

}