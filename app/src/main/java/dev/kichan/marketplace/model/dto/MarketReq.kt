package dev.kichan.marketplace.model.dto

data class MarketReq(
    val marketName: String,
    val description: String,
    val operationHours: String,
    val closedDays: String,
    val phoneNumber: String,
    val address: String,
    val major: String
)