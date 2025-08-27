package dev.kichan.marketplace.model.dto

data class TopPopularCouponRes(
    val couponId: Long,
    val couponName: String,
    val marketId: Long,
    val marketName: String,
    val thumbnail: String,
    val issuedCount: Long
)