package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.memberCoupon

import com.google.gson.annotations.SerializedName

data class MemberCoupon(
    @SerializedName("memberCouponId") val id: Long,
    val couponId: Long,
    val couponName : String,
    val description : String,
    val deadLine : String,
    val used : Boolean
)