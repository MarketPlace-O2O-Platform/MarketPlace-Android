package dev.kichan.marketplace.model.data

data class PageNationTemplate<T>(
    val body : List<T>,
    val hasNext: Boolean,
)