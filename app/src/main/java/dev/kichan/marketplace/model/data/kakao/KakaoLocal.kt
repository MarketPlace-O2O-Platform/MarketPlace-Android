package dev.kichan.marketplace.model.data.kakao

import dev.kichan.marketplace.model.data.kakao.local.Meta

data class KakaoLocal<D>(
    val documents: List<D>,
    val meta: Meta
)