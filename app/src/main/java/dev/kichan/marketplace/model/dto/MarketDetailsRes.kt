package dev.kichan.marketplace.model.dto

data class MarketDetailsRes(
    val marketId: Long,
    val name: String,
    val description: String,
    val operationHours: String,
    val closedDays: String,
    val phoneNumber: String,
    val address: String,
    val imageResList: List<String>
)