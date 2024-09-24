package dev.kichan.marketplace.ui.page

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun LikePage(navController: NavController) {
    Text(text = "LikePage")
}

@Preview(showBackground = true)
@Composable
fun LikePagePreview() {
    MarketPlaceTheme {
        LikePage(rememberNavController())
    }
}