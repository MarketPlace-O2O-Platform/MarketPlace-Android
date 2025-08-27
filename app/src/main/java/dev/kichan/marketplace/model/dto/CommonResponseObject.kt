package dev.kichan.marketplace.model.dto

data class CommonResponseObject(
    val message: String,
    val response: Map<String, Any>
)