package dev.kichan.marketplace.model.dto

data class TopLatestCouponRes(
    val couponId: Long,
    val couponName: String,
    val marketId: Long,
    val marketName: String,
    val thumbnail: String,
    val couponCreatedAt: String
)