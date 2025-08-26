package dev.kichan.marketplace.model.dto

data class RequestMarketRes(
    val id: Long,
    val name: String,
    val address: String,
    val count: Int
)