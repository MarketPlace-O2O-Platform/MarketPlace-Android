package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class CouponCreateReq(
    @SerializedName("couponName") val name: String,
    val description: String,
    @SerializedName("deadLine") val deadline: String,
    val stock: Int,
)