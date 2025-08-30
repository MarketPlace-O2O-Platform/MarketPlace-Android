package dev.kichan.marketplace.model.dto

import java.time.LocalDateTime

data class CommonResponseNotificationListRes(
    val message: String,
    val response: List<NotificationRes>
)
