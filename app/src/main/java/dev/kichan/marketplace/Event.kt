package dev.kichan.marketplace

data class Event(
    val marketName : String,
    val eventName: String,
    val defaultPrice : Int,
    val eventPrice : Int
)