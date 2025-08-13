package dev.kichan.marketplace.model.data

data class ResponseTemplate<T> (
    val message : String,
    val response : T
)