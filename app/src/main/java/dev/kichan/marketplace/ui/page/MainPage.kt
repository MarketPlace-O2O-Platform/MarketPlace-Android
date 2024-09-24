package dev.kichan.marketplace.ui.page

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.BottomNavigationBar
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun MainPage() {
    val items = listOf(
        Page.Home to Icons.Filled.Home,
        Page.Like to Icons.Filled.ShoppingCart,
        Page.Map to Icons.Filled.Place,
        Page.My to Icons.Filled.Person,
    )

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, pageList = items) }
    ) {
        NavHost(
            navController = navController,
            startDestination = Page.Home.name,
            modifier = Modifier.padding(it),
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            composable(Page.Home.name) { HomePage(navController = navController) }
            composable(Page.Like.name) { LikePage(navController = navController) }
            composable(Page.Map.name) { MapPage(navController = navController) }
            composable(Page.My.name) { MyPage(navController = navController) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MarketPlaceTheme {
        MainPage()
    }
}
