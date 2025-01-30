package dev.kichan.marketplace.model.data.coupon

data class CouponPagenation<T>(
    val couponResDtos: List<T>,
    val hasNext: Boolean,
)