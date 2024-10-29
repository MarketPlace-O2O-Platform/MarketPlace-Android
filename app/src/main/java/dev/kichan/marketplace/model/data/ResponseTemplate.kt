package dev.kichan.marketplace.model.data

import dev.kichan.marketplace.model.data.login.LoginRes

data class ResponseTemplate<T> (
    val message : String,
    val response : T
)