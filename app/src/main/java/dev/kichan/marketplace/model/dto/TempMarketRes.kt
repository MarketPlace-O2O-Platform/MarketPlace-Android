package dev.kichan.marketplace.model.dto

data class TempMarketRes(
    val marketId: Long,
    val marketName: String,
    val marketDescription: String?, // 백엔드 응답에 필드 누락됨 (2025-11-21)
    val thumbnail: String,
    val cheerCount: Int,
    val isCheer: Boolean,
    val dueDate: Int
)