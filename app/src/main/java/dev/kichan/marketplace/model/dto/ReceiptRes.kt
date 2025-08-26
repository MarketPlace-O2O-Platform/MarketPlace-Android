package dev.kichan.marketplace.model.dto

data class ReceiptRes(
    val memberCouponId: Long,
    val receipt: String,
    val account: String,
    val accountNumber: String,
    val isUsed: Boolean
)