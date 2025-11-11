package dev.kichan.marketplace.model.services

import dev.kichan.marketplace.model.dto.CommonResponseNotificationPageResNotificationRes
import dev.kichan.marketplace.model.dto.CommonResponseNotificationRes
import dev.kichan.marketplace.model.dto.CommonResponseObject
import dev.kichan.marketplace.model.dto.FcmRequest
import dev.kichan.marketplace.model.dto.NotificationReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

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