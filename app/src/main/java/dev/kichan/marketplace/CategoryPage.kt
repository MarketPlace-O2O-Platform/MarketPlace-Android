package dev.kichan.marketplace

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun CategoryPage(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun CategoryPagePreview() {
    MarketPlaceTheme {
        CategoryPage(rememberNavController())
    }
}