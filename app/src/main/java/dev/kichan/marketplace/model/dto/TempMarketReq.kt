package dev.kichan.marketplace.model.dto

data class TempMarketReq(
    val category: String,
    val marketName: String,
    val description: String,
    val address: String
)