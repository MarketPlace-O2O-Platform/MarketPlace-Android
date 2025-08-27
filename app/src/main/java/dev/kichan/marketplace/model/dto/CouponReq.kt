package dev.kichan.marketplace.model.dto

data class CouponReq(
    val couponName: String,
    val description: String,
    val deadLine: String,
    val stock: Int
)