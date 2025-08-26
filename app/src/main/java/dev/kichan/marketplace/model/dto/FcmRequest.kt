package dev.kichan.marketplace.model.dto

data class FcmRequest(
    val memberId: Long? = null,
    val title: String? = null,
    val body: String? = null
)