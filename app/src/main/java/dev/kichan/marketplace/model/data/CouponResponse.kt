package dev.kichan.marketplace.model.data

import com.google.gson.annotations.SerializedName


// ✅ 1. 데이터 모델(CouponResponse, CouponListResponse) 생성

data class CouponResponse(
    @SerializedName("memberCouponId") val memberCouponId: Long,
    @SerializedName("couponId") val couponId: Long,
    @SerializedName("couponName") val couponName: String,
    @SerializedName("description") val description: String,
    @SerializedName("deadLine") val deadLine: String,  // ISO 8601 형식
    @SerializedName("used") val used: Boolean,

    @SerializedName("imageUrl") val imageUrl: String? = null
)

data class CouponListResponse<T>(
    @SerializedName("couponResDtos") val couponResDtos: List<T> = emptyList(),
    @SerializedName("hasNext") val hasNext: Boolean
)


data class CouponUseResponse(
    @SerializedName("couponId") val couponId: Long,
    @SerializedName("isUsed") val isUsed: Boolean
)

