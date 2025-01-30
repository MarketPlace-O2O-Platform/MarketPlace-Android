package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class PopularCpouponRes(
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    val marketId: Long,
    val marketName: String,
    val address: String,
    val thumbnail: String,
    val isAvailable: Boolean,
    @SerializedName("isMemberIssued") val isSued: Boolean,
    val issuedCount: Int,
)