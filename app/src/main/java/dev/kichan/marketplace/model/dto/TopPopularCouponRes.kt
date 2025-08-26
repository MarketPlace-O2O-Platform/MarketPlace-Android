package dev.kichan.marketplace.model.dto

data class TopPopularCouponRes(
    val couponId: Long? = null,
    val couponName: String? = null,
    val marketId: Long? = null,
    val marketName: String? = null,
    val thumbnail: String? = null,
    val issuedCount: Long? = null
)