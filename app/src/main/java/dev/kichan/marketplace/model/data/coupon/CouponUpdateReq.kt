package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.coupon

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CouponUpdateReq (
    @SerializedName("couponName")
    val name: String,
    val description : String,
    @SerializedName("deadLine") val deadline: LocalDate,
    val stock: Int,
)