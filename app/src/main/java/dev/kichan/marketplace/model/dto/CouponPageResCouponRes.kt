package dev.kichan.marketplace.model.dto

data class CouponPageResCouponRes(
    val couponResDtos: List<CouponRes>,
    val hasNext: Boolean
)