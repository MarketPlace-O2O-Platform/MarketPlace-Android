package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class CouponHiddenRes(
    @SerializedName("couponId") val id: Long,
    val hidden : Boolean,
)