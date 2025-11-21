package dev.kichan.marketplace.model.dto

data class IssuedCouponRes(
    val memberCouponId: Long,
    val couponId: Long,
    val marketName: String,
    val thumbnail: String,
    val couponName: String,
    val description: String,
    val used: Boolean,
    val couponType: String,
    val isSubmit: Boolean = false,
    val deadLine: String?,
    val expired: Boolean
)