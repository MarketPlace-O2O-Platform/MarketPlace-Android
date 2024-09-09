package dev.kichan.marketplace.model.data.kakao.local

data class SameName(
    val keyword: String,
    val region: List<String>,
    val selected_region: String
)