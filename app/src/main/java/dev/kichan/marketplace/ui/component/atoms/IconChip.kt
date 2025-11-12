package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun IconChip(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    title: String,
    contentColor: Color,
    backgroundColor: Color,
    shadowElevation: Dp = 3.dp,
) {
    val shape = RoundedCornerShape(58.dp)

    Row(
        modifier = modifier
            .shadow(elevation = shadowElevation, shape = shape)
            .background(color = backgroundColor, shape = shape)
            .clickable { onClick() }
            .padding(vertical = 9.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight.Medium,
                color = contentColor
            )
        )
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