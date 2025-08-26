import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*

interface NotificationsService {

    @GET("/api/notifications")
    suspend fun getNotificationList(@Query("notificationId") notificationId: Long? = null, @Query("type") type: String? = null, @Query("size") size: Int? = null): Response<CommonResponseNotificationPageResNotificationRes>

    @POST("/api/notifications")
    suspend fun createNotification(@Body body : NotificationReq): Response<CommonResponseNotificationRes>

    @PATCH("/api/notifications")
    suspend fun setNotificationRead(@Query("notificationId") notificationId: Long? = null): Response<CommonResponseObject>

    @POST("/api/notifications/test")
    suspend fun sendFcmMessage(@Body body : FcmRequest): Response<CommonResponseObject>

    @PATCH("/api/notifications/all")
    suspend fun setAllNotificationRead(): Response<CommonResponseObject>

}