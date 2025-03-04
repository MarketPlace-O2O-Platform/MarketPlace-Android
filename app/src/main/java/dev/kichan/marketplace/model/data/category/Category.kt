package dev.kichan.marketplace.model.data.category

// 카테고리 (Category)
data class Category(
    val id: Long,
    val major: String,              // 대분류
    val minor: String               // 소분류
)