package dev.kichan.marketplace.model.data.market

import com.google.gson.annotations.SerializedName
import dev.kichan.marketplace.model.data.image.ImageRes

data class MarketDetailRes(
    @SerializedName("marketId") val id: Long,
    val name: String,
    val description: String,
    val operationHours: String,
    val closedDays: String,
    val phoneNumber: String,
    val address: String,
    val imageResList: List<ImageRes>
)