package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class IssuedCouponRes(
    val couponId: Long,
    val couponName: String,
    @SerializedName("couponDescription") val description: String?,
    val deadLine: String,
    val isAvailable: Boolean,
    val isMemberIssued: Boolean
)