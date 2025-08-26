package dev.kichan.marketplace.model.dto

data class MarketDetailsRes(
    val marketId: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val operationHours: String? = null,
    val closedDays: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val imageResList: List<String>? = null
)