package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.ui.theme.Gray_9
import dev.kichan.marketplace.ui.theme.PretendardFamily

val appBarModifier = Modifier
    .fillMaxWidth()
    .padding(top = 18.dp, bottom = 12.dp, start = 20.dp, end = 20.dp)

val titleStyle = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = PretendardFamily,
    color = Gray_9
)

@Composable
fun IconAppBar(title: String, vararg icons: Pair<ImageVector, () -> Unit>) {
    Row(
        modifier = appBarModifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title, style = titleStyle,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (icon in icons) {
                Icon(
                    imageVector = icon.first,
                    contentDescription = null,
                    Modifier.clickable { icon.second() },
                    tint = Color(0xff121212)
                )
            }
        }
    }
}