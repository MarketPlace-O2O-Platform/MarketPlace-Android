package dev.kichan.marketplace.model.dto

data class TempMarketRes(
    val marketId: Long,
    val marketName: String,
    val marketDescription: String,
    val thumbnail: String,
    val cheerCount: Int,
    val isCheer: Boolean,
    val dueDate: Int
)