package dev.kichan.marketplace.model.data

import com.google.gson.annotations.SerializedName

data class CouponMemberRes(
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    val description: String,
    val deadLine: String
)

data class LatestCoupon(
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    val marketId : Long,
    val marketName: String,
    val address : String,
    val thumbnail: String,
    @SerializedName("couponCreatedAt") val CREATE_AT : String,
)

data class ClosingCouponRes(
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    val marketId : Long,
    val marketName: String,
    val thumbnail: String,
)