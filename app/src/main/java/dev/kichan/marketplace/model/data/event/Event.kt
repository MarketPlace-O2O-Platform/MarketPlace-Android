package dev.kichan.marketplace.model.data.event

data class Event(
    val marketName : String,
    val eventName: String,
    val defaultPrice : Int,
    val eventPrice : Int
)