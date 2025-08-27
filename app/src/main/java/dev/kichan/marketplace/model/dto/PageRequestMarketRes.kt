package dev.kichan.marketplace.model.dto

data class PageRequestMarketRes(
    val totalPages: Int,
    val totalElements: Long,
    val first: Boolean,
    val last: Boolean,
    val sort: SortObject,
    val size: Int,
    val content: List<String>,
    val number: Int,
    val numberOfElements: Int,
    val pageable: PageableObject,
    val empty: Boolean
)