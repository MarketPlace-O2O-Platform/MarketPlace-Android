package dev.kichan.marketplace.model.dto

data class CouponPageResIssuedCouponRes(
    val couponResDtos: List<IssuedCouponRes>,
    val hasNext: Boolean
)