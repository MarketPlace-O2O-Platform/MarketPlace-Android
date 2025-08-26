package dev.kichan.marketplace.model.dto

data class CouponReq(
    val couponName: String? = null,
    val description: String? = null,
    val deadLine: String? = null,
    val stock: Int? = null
)