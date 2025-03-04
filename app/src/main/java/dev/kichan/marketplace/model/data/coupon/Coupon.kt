package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class Coupon(
    @SerializedName("couponId") val id: Long,
    val marketId: Long,
    @SerializedName("couponName") val name: String,
    val description: String?,
    @SerializedName("deadLine") val deadline: String,
    val stock: Int,
    @SerializedName("hidden") val isHidden: Boolean,
    val createdAt: String,
)

data class LatestCouponRes(
    @SerializedName("couponId") val id: Long,
    val marketId: Long,
    @SerializedName("couponName") val name: String,
    val description: String?,
    val createdAt: String,
)
