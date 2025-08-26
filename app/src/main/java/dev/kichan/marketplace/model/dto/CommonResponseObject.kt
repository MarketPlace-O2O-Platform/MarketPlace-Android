package dev.kichan.marketplace.model.dto

data class CommonResponseObject(
    val message: String? = null,
    val response: Map<String, Any>? = null
)