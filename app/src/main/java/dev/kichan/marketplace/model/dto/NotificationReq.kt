package dev.kichan.marketplace.model.dto

data class NotificationReq(
    val title: String,
    val body: String,
    val targetId: Long,
    val targetType: String
)