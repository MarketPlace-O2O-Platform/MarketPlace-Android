package dev.kichan.marketplace.model.data.like

import com.google.gson.annotations.SerializedName

data class TempMarketRes(
    @SerializedName("marketId") val id: Long,
    @SerializedName("marketName") val name: Long,
    @SerializedName("marketDescription") val description: String,
    val thumbnail: String,
    val cheerCount: Int,
    val isCheer: Boolean,
)