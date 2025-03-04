package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class IssuedCouponRes(
    val memberCouponId: Long,
    val couponId: Long,
    val couponName: String,
    val description: String,
    val deadLine: String,
    @SerializedName("used") val isUsed: Boolean,
)