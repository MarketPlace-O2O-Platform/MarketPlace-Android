import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*

interface TempmarketsService {

    @GET("/api/tempMarkets")
    suspend fun getTempMarket(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("category") category: String? = null, @Query("count") count: Int? = null): Response<CommonResponseTempMarketPageResTempMarketRes>

    @GET("/api/tempMarkets/search")
    suspend fun searchMarket(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("pageSize") pageSize: Int? = null, @Query("name") name: String? = null): Response<CommonResponseTempMarketPageResTempMarketRes>

    @GET("/api/tempMarkets/cheer")
    suspend fun getTempMarketPage(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("lastCheerCount") lastCheerCount: Int? = null, @Query("count") count: Int? = null): Response<CommonResponseTempMarketPageResTempMarketRes>

}