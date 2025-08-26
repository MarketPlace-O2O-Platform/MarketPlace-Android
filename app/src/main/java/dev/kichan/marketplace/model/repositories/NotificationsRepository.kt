import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import NotificationsService

@Singleton
class NotificationsRepository @Inject constructor(
    private val service: NotificationsService
) {

    suspend fun getNotificationList(@Query("notificationId") notificationId: Long? = null, @Query("type") type: String? = null, @Query("size") size: Int? = null): Response<CommonResponseNotificationPageResNotificationRes> {
        return service.getNotificationList(notificationId, type, size)
    }

    suspend fun createNotification(@Body body : NotificationReq): Response<CommonResponseNotificationRes> {
        return service.createNotification(body)
    }

    suspend fun setNotificationRead(@Query("notificationId") notificationId: Long? = null): Response<CommonResponseObject> {
        return service.setNotificationRead(notificationId)
    }

    suspend fun sendFcmMessage(@Body body : FcmRequest): Response<CommonResponseObject> {
        return service.sendFcmMessage(body)
    }

    suspend fun setAllNotificationRead(): Response<CommonResponseObject> {
        return service.setAllNotificationRead()
    }

}