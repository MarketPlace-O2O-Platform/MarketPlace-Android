package dev.kichan.marketplace.model.data.coupon

data class IssuedCouponListResponse(
    val couponResDtos: List<IssuedCouponRes>,
    val hasNext: Boolean
)
