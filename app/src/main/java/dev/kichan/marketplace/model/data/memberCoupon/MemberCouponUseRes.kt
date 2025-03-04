package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.memberCoupon

import com.google.gson.annotations.SerializedName

data class MemberCouponUseRes (
    @SerializedName("couponId") val id: Long,
    val isUsed : Boolean,
)