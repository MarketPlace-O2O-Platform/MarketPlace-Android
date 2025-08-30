package dev.kichan.marketplace.model.repository

import dev.kichan.marketplace.model.dto.CommonResponseNotificationListRes
import dev.kichan.marketplace.model.dto.CommonResponseNotificationPageResNotificationRes
import dev.kichan.marketplace.model.services.NotificationService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val service: NotificationService
) {
    suspend fun getNotificationList(): Response<CommonResponseNotificationPageResNotificationRes> {
        return service.getNotificationList()
    }
}
