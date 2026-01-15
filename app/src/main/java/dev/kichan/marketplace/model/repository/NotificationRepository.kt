package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponseNotificationPageResNotificationRes
import dev.kichan.marketplace.model.dto.CommonResponseNotificationRes
import dev.kichan.marketplace.model.dto.NotificationReq
import dev.kichan.marketplace.model.services.NotificationService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val service: NotificationService
) {
    suspend fun getNotificationList(
        notificationId: Long? = null,
        type: String? = null,
        size: Int? = null
    ): Response<CommonResponseNotificationPageResNotificationRes> {
        return service.getNotificationList(notificationId, type, size)
    }

    suspend fun createNotification(
        body: NotificationReq
    ): Response<CommonResponseNotificationRes> {
        return service.createNotification(body)
    }

    suspend fun readNotification(
        notificationId: Long
    ): Response<Void> {
        return service.readNotification(notificationId)
    }

    suspend fun readNotificationAll(): Response<Void> {
        return service.readNotificationAll()
    }
}
