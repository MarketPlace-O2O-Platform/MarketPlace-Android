package dev.kichan.marketplace.model.dto

data class BetaCouponRes(
    val betaCouponId: Long,
    val marketName: String,
    val couponName: String,
    val couponDetail: String,
    val image: String,
    val isUsed: Boolean,
    val isPromise: Boolean
)