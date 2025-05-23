package dev.kichan.marketplace.model.data.market

import com.google.gson.annotations.SerializedName

data class MarketCreateReq(
    @SerializedName("marketName") val name: String,
    val description: String,
    val operationHours: String,
    val closedDays: String,
    val phoneNumber: String,
    val major : String?,
    val address : String,
)