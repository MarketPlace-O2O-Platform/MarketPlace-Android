package dev.kichan.marketplace.model.dto

data class CouponRes(
    val couponId: Long,
    val couponName: String,
    val couponDescription: String,
    val deadLine: String,
    val stock: Int,
    val isHidden: Boolean,
    val isAvailable: Boolean,
    val isMemberIssued: Boolean,
    val couponType: String,
    val marketId: Long,
    val marketName: String,
    val address: String,
    val thumbnail: String,
    val couponCreatedAt: String,
    val issuedCount: Long
)