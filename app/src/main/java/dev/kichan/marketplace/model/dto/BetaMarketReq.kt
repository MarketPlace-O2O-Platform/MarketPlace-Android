package dev.kichan.marketplace.model.dto

data class BetaMarketReq(
    val marketName: String,
    val couponName: String,
    val couponDetail: String,
    val major: String,
    val isPromise: Boolean
)