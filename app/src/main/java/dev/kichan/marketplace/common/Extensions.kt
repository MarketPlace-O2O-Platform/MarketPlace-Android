package dev.kichan.marketplace.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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

/**
 * Ripple 효과 없이 클릭 가능한 Modifier
 */
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}