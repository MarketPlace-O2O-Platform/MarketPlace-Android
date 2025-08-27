package dev.kichan.marketplace.model.dto

data class CouponPageResPaybackRes(
    val couponResDtos: List<PaybackRes>,
    val hasNext: Boolean
)