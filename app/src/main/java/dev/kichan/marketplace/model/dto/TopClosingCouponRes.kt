package dev.kichan.marketplace.model.dto

data class TopClosingCouponRes(
    val couponId: Long,
    val couponName: String,
    val deadline: String?,
    val marketId: Long,
    val marketName: String,
    val thumbnail: String
)