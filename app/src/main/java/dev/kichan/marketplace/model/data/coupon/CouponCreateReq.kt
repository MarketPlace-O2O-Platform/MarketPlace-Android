package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime

data class CouponCreateReq(
    @SerializedName("couponName") val name: String,
    val description: String?,
    @SerializedName("deadLine") val deadline: String,
    val stock: Int,
)