package dev.kichan.marketplace.model.dto

data class NotificationRes(
    val id: Long,
    val title: String,
    val body: String,
    val targetId: Long,
    val targetType: String,
    val isRead: Boolean,
    val createdAt: String
)