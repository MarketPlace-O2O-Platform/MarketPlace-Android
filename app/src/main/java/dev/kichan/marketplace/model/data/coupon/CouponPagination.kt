package dev.kichan.marketplace.model.data.coupon

data class CouponPagination<T>(
    val couponResDtos: List<T>,
    val hasNext: Boolean,
)