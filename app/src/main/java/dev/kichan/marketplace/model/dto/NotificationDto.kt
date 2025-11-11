package dev.kichan.marketplace.model.dto

data class CommonResponseNotificationListRes(
    val message: String,
    val response: List<NotificationRes>
)
