package dev.kichan.marketplace.model.dto

data class PageTempMarketDetailRes(
    val totalPages: Int? = null,
    val totalElements: Long? = null,
    val first: Boolean? = null,
    val last: Boolean? = null,
    val sort: SortObject? = null,
    val size: Int? = null,
    val content: List<String>? = null,
    val number: Int? = null,
    val numberOfElements: Int? = null,
    val pageable: PageableObject? = null,
    val empty: Boolean? = null
)