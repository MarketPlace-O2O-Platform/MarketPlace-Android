package dev.kichan.marketplace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun MoreViewTitle(modifier: Modifier = Modifier, title: String, onMoreClick: () -> Unit) {
    Row(
        modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = PretendardFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xff121212)
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onMoreClick() }
        ) {
            Text(
                text = "더보기",
                style = TextStyle(
                    fontFamily = PretendardFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff7D7D7D)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xff7D7D7D),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoreViewtitlePreview() {
    MarketPlaceTheme {
        MoreViewTitle(title = "요즘 많이 찾는 제휴 이벤트", onMoreClick = {})
    }
}