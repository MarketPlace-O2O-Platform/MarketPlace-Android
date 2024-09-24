package dev.kichan.marketplace.ui.page

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun CategoryPage(navController: NavController) {
    Text(text = "CategoryPage")
}

@Preview(showBackground = true)
@Composable
fun CategoryPagePreview() {
    MarketPlaceTheme {
        CategoryPage(rememberNavController())
    }
}