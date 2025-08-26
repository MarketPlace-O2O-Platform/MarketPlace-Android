package dev.kichan.marketplace.model.dto

data class ReceiptRes(
    val memberCouponId: Long? = null,
    val receipt: String? = null,
    val account: String? = null,
    val accountNumber: String? = null,
    val isUsed: Boolean? = null
)