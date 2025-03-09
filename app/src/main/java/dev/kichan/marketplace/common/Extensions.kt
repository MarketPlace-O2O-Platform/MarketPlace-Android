package dev.kichan.marketplace.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toUsFormat() : String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    return this.format(formatter)
}

fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    return LocalDateTime.parse(this, formatter)
}