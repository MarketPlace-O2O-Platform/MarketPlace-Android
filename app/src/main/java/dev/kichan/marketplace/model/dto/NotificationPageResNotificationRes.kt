package dev.kichan.marketplace.model.dto

data class NotificationPageResNotificationRes(
    val notificationResList: List<NotificationRes>,
    val hasNext: Boolean
)