package dev.kichan.marketplace.model.data

import dev.kichan.marketplace.model.data.market.MarketRes

data class MarketPageNationRes(
    val marketResDtos: List<MarketRes>,
    val hasNext : Boolean,
)