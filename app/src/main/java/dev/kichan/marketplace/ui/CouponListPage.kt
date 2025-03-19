package dev.kichan.marketplace.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun CouponListPage(
    navController: NavHostController = rememberNavController(),
    type: String,
) {
    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(type)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponListPagePreview() {
    MarketPlaceTheme() {
        CouponListPage(type = "popular")
    }
}