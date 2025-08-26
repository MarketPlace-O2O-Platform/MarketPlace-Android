package dev.kichan.marketplace.model.dto

data class MarketRes(
    val marketId: Long? = null,
    val marketName: String? = null,
    val marketDescription: String? = null,
    val address: String? = null,
    val thumbnail: String? = null,
    val isFavorite: Boolean? = null,
    val isNewCoupon: Boolean? = null,
    val favoriteModifiedAt: String? = null
)