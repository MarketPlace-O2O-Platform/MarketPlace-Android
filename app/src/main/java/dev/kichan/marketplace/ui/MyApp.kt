package dev.kichan.marketplace.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.ui.page.EventListPage
import dev.kichan.marketplace.ui.page.ApiTestPage
import dev.kichan.marketplace.ui.page.DetailPage
import dev.kichan.marketplace.ui.page.HomePage
import dev.kichan.marketplace.ui.page.LikePage
import dev.kichan.marketplace.ui.page.MapPage
import dev.kichan.marketplace.ui.page.MyPage
import dev.kichan.marketplace.ui.page.LoginPage
import dev.kichan.marketplace.ui.page.CouponPage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Page.Login.name,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        navigation(route = Page.Main.name, startDestination = Page.Home.name) {
            composable(Page.Home.name) {
                HomePage(
                    navController = navController,
                )
            }
            composable(Page.Like.name) { LikePage(navController = navController) }
            composable(Page.Map.name) { MapPage(navController = navController) }
            composable(Page.My.name) {
                MyPage(
                    navController = navController,
                )
            }
            composable(Page.CouponHam.name) { CouponPage(navController = navController) }

            composable("${Page.EventDetail.name}/{id}") {
                it.arguments?.getString("id")?.let { id ->
                    DetailPage(navController, id)
                }
            }
        }
        composable(route = Page.Login.name) {
            LoginPage(navController = navController)
        }

        composable("${Page.CategoryEventList.name}/{category}") {
            it.arguments?.getString("category")?.let { category ->
                EventListPage(
                    navController = navController,
                    category = LargeCategory.valueOf(category)
                )
            }
        }

        composable("${Page.EventList.name}/{title}") {
            it.arguments?.getString("title")?.let { title ->
                EventListPage(navController = navController, title = title)
            }
        }

//        composable(Page.LocalApiTestPage.name) {
//            ApiTestPage(couponViewModel)
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MarketPlaceTheme {
        MyApp()
    }
}