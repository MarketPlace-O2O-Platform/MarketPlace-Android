package dev.kichan.marketplace.model.data.event

import androidx.annotation.DrawableRes

data class Event2(
    val marketName : String,
    val eventName: String,
    val location:String,
    @DrawableRes val imageRes: Int

)