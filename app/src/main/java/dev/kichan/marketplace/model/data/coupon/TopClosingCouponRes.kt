package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class TopClosingCouponRes(
    @SerializedName("couponId") val id: Long,
    val marketId: Long,
    @SerializedName("couponName") val name: String,
    val description: String?,
    @SerializedName("deadLine") val deadline: String,
    val thumbnail: String,
)