package dev.kichan.marketplace.ui

import android.util.Log
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
import dev.kichan.marketplace.ui.page.LoginPage
import dev.kichan.marketplace.ui.page.SearchPage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.ui.page.MarketListPage
import dev.kichan.marketplace.ui.page.ReceiptUploadPage
import dev.kichan.marketplace.ui.page.SplashPage
import dev.kichan.marketplace.ui.page.CouponListPage
import dev.kichan.marketplace.ui.page.CurationPage
import dev.kichan.marketplace.ui.page.AlertPage
import dev.kichan.marketplace.ui.page.MyPage2
import dev.kichan.marketplace.ui.page.RequestPage

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Page.Splash.name,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        composable(Page.Splash.name) {
            SplashPage(
                navController = navController,
            )
        }
        navigation(route = Page.Main.name, startDestination = Page.Home.name) {
            composable(Page.Home.name) {
                HomePage(
                    navController = navController,
                )
            }
            composable(Page.Like.name) {
                LikePage(
                    navController = navController,
                )
            }
            composable(Page.Map.name) {
                MapPage(
                    navController = navController,
                )
            }
            composable(Page.My.name) {
                MyPage2(
                    navController = navController,
                )
            }
            composable(Page.Search.name) { SearchPage() }

            composable("${Page.EventDetail.name}/{id}") {
                it.arguments?.getString("id")?.let { id ->
                    Log.d("eventDetail", "id : $id")
                    MarketDetailPage(navController, id.toLong())
                }
            }
        }
        composable(route = Page.Login.name) {
            LoginPage(
                navController = navController,
            )
        }
        composable(route = "${Page.MarketListPage.name}/{category}") {
            it.arguments?.getString("category")?.let { category ->
                MarketListPage(
                    nacController = navController,
                    category = LargeCategory.valueOf(category)
                )
            }
        }
        composable(route = "${Page.CouponListPage.name}/{type}") {
            it.arguments?.getString("type")?.let { type ->
                if (!listOf("popular", "latest").contains(type)) {
                    throw Exception("Invalid type")
                }

                CouponListPage(
                    navController = navController,
                    type = type
                )
            }
        }
        composable(route = Page.My2.name) {
            MyPage2(
                navController = navController,
            )
        }

        composable(Page.ReceptUploadPage.name + "/{couponId}") {
            it.arguments?.getString("couponId")?.let {
                ReceiptUploadPage(
                    navController = navController,
                    couponId = it.toLong()
                )
            }
        }
        composable(Page.CurationPage.name) {
            CurationPage(nacController = navController)
        }
        composable(Page.AlertPage.name) {
            AlertPage(navController)
        }
        composable(Page.RequestPage.name) {
            RequestPage(navController)
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