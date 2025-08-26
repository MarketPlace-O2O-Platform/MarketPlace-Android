package dev.kichan.marketplace.model.dto

data class TempMarketReq(
    val category: String? = null,
    val marketName: String? = null,
    val description: String? = null,
    val address: String? = null
)