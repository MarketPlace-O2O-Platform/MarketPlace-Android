package dev.kichan.marketplace.model.dto

data class TempMarketDetailRes(
    val marketId: Long,
    val marketName: String,
    val description: String,
    val address: String,
    val thumbnail: String,
    val cheerCount: Int,
    val isHidden: Boolean
)