package dev.kichan.marketplace.ui.component

import LargeCategory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun CategoryTap(
    modifier: Modifier = Modifier,
    selectedCategory: LargeCategory,
    onSelected: (LargeCategory) -> Unit
) {
    val itemStyle = TextStyle(
        fontFamily = PretendardFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    )
    LazyRow(
        modifier = modifier.background(Color.White)
    ) {
        LargeCategory.entries.map {
            item {
                Surface(
                    modifier = Modifier
                        .clickable { onSelected(it) }
                ) {
                    Text(
                        text = it.nameKo,
                        textAlign = TextAlign.Center,
                        style = itemStyle.copy(
                            color = if (selectedCategory == it) Color(0xff121212) else Color(
                                0xff7D7D7D
                            )
                        ),
                        modifier = Modifier
                            .padding(bottom = 8.dp, top = 16.dp, start = 20.dp, end = 20.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryTapPreview() {
    MarketPlaceTheme {
        CategoryTap(
            selectedCategory = LargeCategory.Food,
            onSelected = {}
        )
    }
}