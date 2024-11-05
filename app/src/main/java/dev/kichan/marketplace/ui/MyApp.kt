package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui

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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.gson.annotations.SerializedName
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.BottomNavigationBar
import dev.kichan.marketplace.ui.page.DetailPage
import dev.kichan.marketplace.ui.page.HomePage
import dev.kichan.marketplace.ui.page.LikePage
import dev.kichan.marketplace.ui.page.MapPage
import dev.kichan.marketplace.ui.page.MyPage
import dev.kichan.marketplace.ui.page.PopularEventPage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun MyApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Page.Main.name,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        navigation(route = Page.Main.name, startDestination = Page.Home.name) {
            composable(Page.Home.name) { HomePage(navController = navController) }
            composable(Page.Like.name) { LikePage(navController = navController) }
            composable(Page.Map.name) { MapPage(navController = navController) }
            composable(Page.My.name) { MyPage(navController = navController) }
            composable(Page.EventDetail.name) { DetailPage() }
        }

        composable("${Page.PopularEvent.name}/{category}") {
            it.arguments?.getString("category")?.let { category ->
                PopularEventPage(navController = navController, category = category)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MarketPlaceTheme {
        MyApp()
    }
}