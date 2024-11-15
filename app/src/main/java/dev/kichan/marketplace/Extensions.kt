package dev.kichan.marketplace.ui.component.dev.kichan.marketplace

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toUsFormat() : String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    return this.format(formatter)
}