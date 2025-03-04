package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class ClosingCouponRes(
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    val deadline: String,
    val marketId: Long,
    val marketName: String,
    val thumbnail: String,
)