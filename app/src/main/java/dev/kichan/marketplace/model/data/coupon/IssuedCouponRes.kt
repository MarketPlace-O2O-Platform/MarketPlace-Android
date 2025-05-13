package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class IssuedCouponRes(
    @SerializedName("memberCouponId") val id: Long,
    val couponId: Long,
    val thumbnail: String,
    val couponName: String,
    val description: String?,
    val deadLine: String,
    @SerializedName("used") val isUsed: Boolean
)