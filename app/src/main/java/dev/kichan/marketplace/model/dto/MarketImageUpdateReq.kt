package dev.kichan.marketplace.model.dto

data class MarketImageUpdateReq(
    val deletedImageIds: List<Long>,
    val changedSequences: Map<String, Any>,
    val addedImageSequences: List<Int>
)