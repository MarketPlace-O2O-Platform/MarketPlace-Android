package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.component.CurationCard
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun LikePage(navController: NavController) {
    Column {
        LazyRow(
            contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) {
                CurationCard(
                    modifier = Modifier.width(284.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LikePagePreview() {
    MarketPlaceTheme {
        LikePage(rememberNavController())
    }
}