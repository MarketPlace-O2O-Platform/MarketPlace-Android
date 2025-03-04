package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market

import com.google.gson.annotations.SerializedName
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.image.ImageRes

data class MarketDetailRes(
    @SerializedName("marketId") val id: Long,
    val name: String,
    val description: String,
    val operationHours: String,
    val closedDay: String,
    val phoneNumber: String,
    val address: String,
    @SerializedName("imageResList") val images: List<ImageRes>
)