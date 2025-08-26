package dev.kichan.marketplace.model.dto

data class MarketRes(
    val marketId: Long,
    val marketName: String,
    val marketDescription: String,
    val address: String,
    val thumbnail: String,
    val isFavorite: Boolean,
    val isNewCoupon: Boolean,
    val favoriteModifiedAt: String
)