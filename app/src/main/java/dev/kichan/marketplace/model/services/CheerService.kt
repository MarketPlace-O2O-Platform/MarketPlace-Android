import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*

interface CheerService {

    @POST("/api/cheer")
    suspend fun createCheer(@Query("tempMarketId") tempMarketId: Long? = null): Response<CommonResponseObject>

}