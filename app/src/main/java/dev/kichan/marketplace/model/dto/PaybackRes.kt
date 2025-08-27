package dev.kichan.marketplace.model.dto

data class PaybackRes(
    val couponId: Long,
    val couponName: String,
    val couponDescription: String,
    val isHidden: Boolean,
    val isMemberIssued: Boolean,
    val couponType: String
)