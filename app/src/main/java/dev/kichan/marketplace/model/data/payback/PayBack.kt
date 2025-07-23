package dev.kichan.marketplace.model.data.payback

data class PayBack(
    val couponId: Long,
    val couponName: String,
    val couponDescription: String,
    val isHidden: Boolean,
    val couponType : String
)