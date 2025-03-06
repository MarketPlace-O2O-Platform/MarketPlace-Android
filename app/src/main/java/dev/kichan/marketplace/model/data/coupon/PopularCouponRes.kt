package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class PopularCouponRes(
    //todo: 필드 입력 다 안함
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    @SerializedName("couponDescription") val description: String,
    val marketId: Long,
    val marketName: String,
    val thumbnail: String,
)