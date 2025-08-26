package dev.kichan.marketplace.model.dto

data class TopClosingCouponRes(
    val couponId: Long? = null,
    val couponName: String? = null,
    val deadline: String? = null,
    val marketId: Long? = null,
    val marketName: String? = null,
    val thumbnail: String? = null
)