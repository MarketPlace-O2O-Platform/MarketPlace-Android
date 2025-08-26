package dev.kichan.marketplace.model.dto

data class NotificationReq(
    val title: String? = null,
    val body: String? = null,
    val targetId: Long? = null,
    val targetType: String? = null
)