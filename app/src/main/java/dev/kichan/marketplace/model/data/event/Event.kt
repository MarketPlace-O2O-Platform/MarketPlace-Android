package dev.kichan.marketplace.model.data.event

import androidx.annotation.DrawableRes

data class Event(
    val id: String,
    val marketName : String,
    val eventName: String,
    val defaultPrice : Int,
    val eventPrice : Int,
    @DrawableRes val imageRes: Int,
    val url : String? = null,
)