package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CouponCreateReq(
    @SerializedName("couponId") val id: Long,
    @SerializedName("couponName") val name: String,
    val description: String?,
    @SerializedName("deadLine") val deadline: LocalDate,
    val stock: Int,
    @SerializedName("hidden") val isHidden: Boolean,
)