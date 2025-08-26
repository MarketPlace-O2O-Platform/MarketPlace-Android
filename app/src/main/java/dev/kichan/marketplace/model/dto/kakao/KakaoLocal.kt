package dev.kichan.marketplace.model.dto.kakao

import dev.kichan.marketplace.model.dto.kakao.local.Meta

data class KakaoLocal<D>(
    val documents: List<D>,
    val meta: Meta
)