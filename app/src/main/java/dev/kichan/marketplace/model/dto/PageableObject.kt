package dev.kichan.marketplace.model.dto

data class PageableObject(
    val sort: SortObject,
    val offset: Long,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean
)