package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponseNotificationListRes
import dev.kichan.marketplace.model.dto.CommonResponseNotificationPageResNotificationRes
import retrofit2.Response
import retrofit2.http.GET

interface NotificationService {
    @GET("/api/notifications")
    suspend fun getNotificationList(): Response<CommonResponseNotificationPageResNotificationRes>
}
