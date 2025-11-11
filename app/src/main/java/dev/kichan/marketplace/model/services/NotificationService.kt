package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponseNotificationPageResNotificationRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface NotificationService {
    @GET("/api/notifications")
    suspend fun getNotificationList(@Query("type") type: String?): Response<CommonResponseNotificationPageResNotificationRes>

    @PATCH("/api/notifications")
    suspend fun readNotification(
        @Query("notificationId")
        notificationId: Long
    ) : Response<Void>;

    @PATCH("/api/notifications/all")
    suspend fun readNotificationALl() : Response<Void>;
}
