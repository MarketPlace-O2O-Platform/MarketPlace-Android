package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.market

import com.google.gson.annotations.SerializedName
import dev.kichan.marketplace.model.data.market.Market

data class MarketPageRes(
    @SerializedName("marketResDtos") val markets: List<Market>,
    val hasNext: Boolean,
)