package dev.kichan.marketplace.model.dto

data class BetaMarketReq(
    val marketName: String? = null,
    val couponName: String? = null,
    val couponDetail: String? = null,
    val major: String? = null,
    val isPromise: Boolean? = null
)