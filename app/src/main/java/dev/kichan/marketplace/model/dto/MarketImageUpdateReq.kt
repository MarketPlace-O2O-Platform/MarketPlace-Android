package dev.kichan.marketplace.model.dto

data class MarketImageUpdateReq(
    val deletedImageIds: List<Long>? = null,
    val changedSequences: Map<String, Any>? = null,
    val addedImageSequences: List<Int>? = null
)