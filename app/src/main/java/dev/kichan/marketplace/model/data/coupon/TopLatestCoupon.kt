package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName

data class TopLatestCoupon(
    @SerializedName("couponId") val id: Long,
    val marketId: Long,
    @SerializedName("couponName") val name: String,
    val marketName: String,
    val thumbnail: String,
    val isFavorite: Boolean
)