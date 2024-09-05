package dev.kichan.marketplace.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun MapPage(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun MapPagePreview() {
    MarketPlaceTheme {
        MapPage(rememberNavController())
    }
}