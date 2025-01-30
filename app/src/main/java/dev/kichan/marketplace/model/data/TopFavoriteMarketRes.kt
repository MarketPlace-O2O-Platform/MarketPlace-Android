package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data

import com.google.gson.annotations.SerializedName

data class TopFavoriteMarketRes(
    @SerializedName("marketId") val id: Long,
    @SerializedName("marketName") val name: String,
    val thumbnail: String,
    val favorite: Boolean,
)