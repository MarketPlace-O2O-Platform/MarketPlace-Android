import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import FavoritesService

@Singleton
class FavoritesRepository @Inject constructor(
    private val service: FavoritesService
) {

    suspend fun createCoupon_1(@Query("marketId") marketId: Long? = null): Response<CommonResponseObject> {
        return service.createCoupon_1(marketId)
    }

}