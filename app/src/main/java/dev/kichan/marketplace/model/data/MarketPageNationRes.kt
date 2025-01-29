package dev.kichan.marketplace.model.data


data class MarketPageNationRes<T>(
    val marketResDtos: List<T>,
    val hasNext : Boolean,
)