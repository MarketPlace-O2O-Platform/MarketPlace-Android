package dev.kichan.marketplace.model.dto

data class FcmRequest(
    val memberId: Long,
    val title: String,
    val body: String
)