import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import MarketsService

@Singleton
class MarketsRepository @Inject constructor(
    private val service: MarketsService
) {

    suspend fun getMarket(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("category") category: String? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseMarketPageResMarketRes> {
        return service.getMarket(lastPageIndex, category, pageSize)
    }

    suspend fun getMarket_1(@Path("marketId") marketId: Long ): Response<CommonResponseMarketDetailsRes> {
        return service.getMarket_1(marketId)
    }

    suspend fun searchMarket_1(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("pageSize") pageSize: Int? = null, @Query("name") name: String? = null): Response<CommonResponseMarketPageResMarketRes> {
        return service.searchMarket_1(lastPageIndex, pageSize, name)
    }

    suspend fun getMemberFavoriteMarketList(@Query("lastModifiedAt") lastModifiedAt: String? = null, @Query("pageSize") pageSize: Int? = null): Response<CommonResponseMarketPageResMarketRes> {
        return service.getMemberFavoriteMarketList(lastModifiedAt, pageSize)
    }

    suspend fun getMarket_2(@Query("lastPageIndex") lastPageIndex: Long? = null, @Query("category") category: String? = null, @Query("pageSize") pageSize: Int? = null, @Query("address") address: String? = null): Response<CommonResponseMarketPageResMarketRes> {
        return service.getMarket_2(lastPageIndex, category, pageSize, address)
    }

}