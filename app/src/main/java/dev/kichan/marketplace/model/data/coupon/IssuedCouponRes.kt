package dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class IssuedCouponRes(
    @SerializedName("memberCouponId") val id: Long,
    val couponId: Long,
    val thumbnail: String,
    val couponName: String,
    val description: String?,
    val deadLine: String, //날짜 형식 : 2025-12-20T09:55:00.976
    @SerializedName("used") val isUsed: Boolean
)