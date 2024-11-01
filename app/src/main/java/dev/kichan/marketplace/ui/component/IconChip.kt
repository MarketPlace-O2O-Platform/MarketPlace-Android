package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun IconChip(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    title: String,
    contentColor: Color,
    backgroundColor: Color
) {
    Row(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(58.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(20.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = contentColor)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = title, color = contentColor)
    }
}

@Preview(showBackground = true)
@Composable
fun IconChipPreview() {
    MarketPlaceTheme {
        IconChip(
            modifier = Modifier,
            onClick = {},
            icon = Icons.Default.LocationOn,
            title = "지도 보기",
            contentColor = Color(0xffffffff),
            backgroundColor = Color(0xff121212)
        )
    }
}