package dev.kichan.marketplace.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.page.MarketDetailPage
import dev.kichan.marketplace.ui.page.HomePage
import dev.kichan.marketplace.ui.page.LikePage
import dev.kichan.marketplace.ui.page.MapPage
import dev.kichan.marketplace.ui.page.MyPage
import dev.kichan.marketplace.ui.page.LoginPage
import dev.kichan.marketplace.ui.page.CouponPage
import dev.kichan.marketplace.SingleTonViewModel
import dev.kichan.marketplace.ui.page.SearchPage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.ui.page.ApiTestPage
import dev.kichan.marketplace.ui.page.MarketListPage

@Composable
fun MyApp(singlethone: SingleTonViewModel = SingleTonViewModel()) {
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
                    singleTonViewModel = singlethone,
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
            composable(Page.Search.name) { SearchPage() }

            composable("${Page.EventDetail.name}/{id}") {
                it.arguments?.getString("id")?.let { id ->
                    MarketDetailPage(navController, id.toLong())
                }
            }
        }
        composable(route = Page.Login.name) {
            LoginPage(navController = navController, singleTon = singlethone)
        }
        composable(route = "${Page.MarketListPage.name}/{category}") {
            it.arguments?.getString("category")?.let { category ->
                MarketListPage(
                    nacController = navController,
                    _category = LargeCategory.valueOf(category)
                )
            }
        }

        composable(route = "${Page.CouponListPage.name}/{type}") {
            it.arguments?.getString("type")?.let { type ->
                if(!listOf("popular", "latest").contains(type)) {
                    throw Exception("Invalid type")
                }

                CouponListPage(
                    navController = navController,
                    type = type
                )
            }
        }

//        composable("${Page.CategoryEventList.name}/{category}") {
//            it.arguments?.getString("category")?.let { category ->
//                EventListPage(
//                    navController = navController,
//                    category = LargeCategory.valueOf(category)
//                )
//            }
//        }

//        composable("${Page.EventList.name}/{title}") {
//            it.arguments?.getString("title")?.let { title ->
//                EventListPage(navController = navController, title = title)
//            }
//        }

        composable(Page.LocalApiTestPage.name) {
            ApiTestPage()
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