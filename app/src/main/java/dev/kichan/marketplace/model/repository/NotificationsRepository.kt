package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponseNotificationPageResNotificationRes
import dev.kichan.marketplace.model.dto.CommonResponseNotificationRes
import dev.kichan.marketplace.model.dto.CommonResponseObject
import dev.kichan.marketplace.model.dto.FcmRequest
import dev.kichan.marketplace.model.dto.NotificationReq
import dev.kichan.marketplace.model.services.NotificationsService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsRepository @Inject constructor(
    private val service: NotificationsService
) {

    suspend fun getNotificationList(
        @Query("notificationId") notificationId: Long? = null,
        @Query("type") type: String? = null,
        @Query("size") size: Int? = null
    ): Response<CommonResponseNotificationPageResNotificationRes> {
        return service.getNotificationList(notificationId, type, size)
    }

    suspend fun createNotification(@Body body: NotificationReq): Response<CommonResponseNotificationRes> {
        return service.createNotification(body)
    }

    suspend fun setNotificationRead(@Query("notificationId") notificationId: Long? = null): Response<CommonResponseObject> {
        return service.setNotificationRead(notificationId)
    }

    suspend fun sendFcmMessage(@Body body: FcmRequest): Response<CommonResponseObject> {
        return service.sendFcmMessage(body)
    }

    suspend fun setAllNotificationRead(): Response<CommonResponseObject> {
        return service.setAllNotificationRead()
    }

}