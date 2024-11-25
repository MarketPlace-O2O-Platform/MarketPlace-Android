package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class CouponMember(
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    val description: String?,
    @SerializedName("deadLine") val deadline: String,
)