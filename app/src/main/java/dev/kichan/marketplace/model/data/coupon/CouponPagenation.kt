package dev.kichan.marketplace.model.data.coupon

data class CouponPagenation(
    val couponResDtos: List<CouponRes>,
    val hasNext: Boolean,
)