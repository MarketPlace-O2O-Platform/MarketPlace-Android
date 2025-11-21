package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun CategoryTap(
    modifier: Modifier = Modifier,
    selectedCategory: LargeCategory,
    onSelected: (LargeCategory) -> Unit
) {
    val categories = LargeCategory.entries
    val selectedIndex = categories.indexOf(selectedCategory)

    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        containerColor = Color.White,
        indicator = { tabPositions ->
            SecondaryIndicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .height(2.dp),
                color = Color.Black
            )
        },
        divider = {},
        edgePadding = 0.dp
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selectedCategory == category,
                onClick = { onSelected(category) },
                modifier = Modifier
                    .background(Color.White)
                    .defaultMinSize(minWidth = 0.dp)
            ) {
                Text(
                    text = category.nameKo,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = if (selectedCategory == category) Color(0xff121212) else Color(0xff7D7D7D),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryTapPreview() {
    MarketPlaceTheme {
        CategoryTap(
            selectedCategory = LargeCategory.All,
            onSelected = {}
        )
    }
}