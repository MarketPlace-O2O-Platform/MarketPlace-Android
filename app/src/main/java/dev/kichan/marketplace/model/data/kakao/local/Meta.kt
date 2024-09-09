package dev.kichan.marketplace.model.data.kakao.local

data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val same_name: SameName,
    val total_count: Int
)