package dev.kichan.marketplace.model.data.market

import android.text.BoringLayout
import com.google.gson.annotations.SerializedName

data class MarketRes(
    @SerializedName("marketId") val id: Long,
    @SerializedName("marketName") val name: String,
    val address: String,
    val thumbnail: String,
    val isFavorite: Boolean,
    val isNewCoupon: Boolean,
    val favoriteModifiedAt: String,
)