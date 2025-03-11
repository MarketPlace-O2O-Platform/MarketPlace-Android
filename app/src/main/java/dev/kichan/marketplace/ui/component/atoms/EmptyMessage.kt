package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyMessage(
    modifier: Modifier = Modifier,
    message : String = "이벤트가 없습니다."
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(message, modifier = Modifier.padding(vertical = 16.dp))
    }
}