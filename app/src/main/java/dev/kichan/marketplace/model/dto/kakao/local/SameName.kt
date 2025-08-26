package dev.kichan.marketplace.model.dto.kakao.local

data class SameName(
    val keyword: String,
    val region: List<String>,
    val selected_region: String
)