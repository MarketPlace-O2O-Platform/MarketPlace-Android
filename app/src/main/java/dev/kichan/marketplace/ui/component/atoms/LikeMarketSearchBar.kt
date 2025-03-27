package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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
fun LikeMarketSearchBar(modifier: Modifier = Modifier, key: String, onChange: (String) -> Unit) {
    Row(
        modifier = modifier
            .drawBehind {
                drawLine(
                    color = Color(0xff303030),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(
                vertical = 10.dp,
                horizontal = PAGE_HORIZONTAL_PADDING
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //todo: 아이콘 변경, 미래에 내가 하겠지 뭐
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(18.dp)
                .background(Color(0xff5E5E5E))
        )
        Spacer(modifier = Modifier.width(12.dp))
        BasicTextField(
            value = key,
            onValueChange = onChange,
            textStyle = TextStyle(
                fontSize = 14.sp,
                lineHeight = 26.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            )
        ) {
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                if (key.isBlank()) {
                    Text(
                        text = "제휴 할인 받고 싶은 매장을 알려주세요.",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.8.sp,
                            fontFamily = PretendardFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF727272),
                        )
                    )
                }
                it()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LikeMarketSearchbarPreview() {
    MarketPlaceTheme {
        LikeMarketSearchBar(Modifier.fillMaxWidth(), "", {})
    }
}