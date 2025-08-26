package dev.kichan.marketplace.model.dto

data class BetaMarketRes(
    val betaMarketId: Long,
    val marketName: String,
    val couponName: String,
    val couponDetail: String,
    val image: String,
    val isPromise: Boolean
)