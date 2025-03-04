package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class CouponUpdateReq(
    @SerializedName("couponName") val name: String,
    val description: String,
    val deadLine : String,
    val stock : Int
)