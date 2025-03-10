package dev.kichan.marketplace.model.data.market

import com.google.gson.annotations.SerializedName

data class FavoriteMarketRes(
    @SerializedName("marketId") val id: Long,
    val name : String,
    val description: String,
    val address: String,
    val thumbnail : String,
    val isFavorite : Boolean,
    val isNewCoupon : Boolean,
    val favoriteCount: Int
)