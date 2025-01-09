package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.model.data.like

import java.time.LocalDate

data class LikeRequest(
    val marketName: String,
    val likeCount: Int,
    val imageRes: Int,
    val isMyDone: Boolean,
    val isRequestDone: Boolean,
    val deadLine: LocalDate
)