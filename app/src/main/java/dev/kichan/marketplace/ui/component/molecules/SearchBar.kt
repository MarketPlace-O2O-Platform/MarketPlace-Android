package dev.kichan.marketplace.ui.component.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun SearchBar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color(0xff121212),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Spacer(
                modifier = Modifier
                    .background(Color(0xffC6C6C6))
                    .width(1.dp)
                    .height(15.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "가고 싶은 매장을 찾아보세요",
                    style = TextStyle(
                        fontSize = 8.31.sp,
                        lineHeight = 18.sp,
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFB0B0B0),
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    MarketPlaceTheme {
        SearchBar(Modifier.fillMaxWidth()) {

        }
    }
}