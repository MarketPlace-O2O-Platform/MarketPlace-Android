package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponseNotificationPageResNotificationRes
import dev.kichan.marketplace.model.services.NotificationService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val service: NotificationService
) {
    suspend fun getNotificationList(type: String?): Response<CommonResponseNotificationPageResNotificationRes> {
        return service.getNotificationList(type)
    }


    suspend fun readNotification(
        notificationId: Long
    ) : Response<Void> {
        return service.readNotification(notificationId);
    }

    suspend fun readNotificationALl() : Response<Void> {
        return service.readNotificationALl()
    }
}
