package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class CouponRes(
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    @SerializedName("couponDescription") val description: String,
    @SerializedName("deadLine") val deadline: String,
    val stock: Int,
    val isHidden: Boolean,
    val isAvailable: Boolean,
    val isMemberIssued: Boolean,
    val thumbnail: String,
)