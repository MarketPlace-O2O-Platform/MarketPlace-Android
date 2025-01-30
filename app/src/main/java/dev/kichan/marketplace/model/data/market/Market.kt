package dev.kichan.marketplace.model.data.market

import com.google.gson.annotations.SerializedName

data class Market(
    @SerializedName("marketId") val id: Long,
    val name: String,
    val description: String,
    val operationHours: String,
    val closedDays: String,
    val phoneNumber: String,
    val address: String,
    val thumbnail: String,
)