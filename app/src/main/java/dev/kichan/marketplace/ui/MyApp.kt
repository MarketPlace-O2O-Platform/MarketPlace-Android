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
import dev.kichan.marketplace.ui.page.MyPage
import dev.kichan.marketplace.ui.page.LoginPage
import dev.kichan.marketplace.SingleTonViewModel
import dev.kichan.marketplace.ui.page.SearchPage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.ui.page.ApiTestPage
import dev.kichan.marketplace.ui.page.MarketListPage
import dev.kichan.marketplace.ui.page.ReceivedCouponsScreen
import dev.kichan.marketplace.ui.page.SplashPage
import dev.kichan.marketplace.viewmodel.TempMarketViewModel
import dev.kichan.marketplace.viewmodel.AuthViewModel
import dev.kichan.marketplace.viewmodel.CouponViewModel
import dev.kichan.marketplace.viewmodel.MarketViewModel

@Composable
fun MyApp(
    singlethone: SingleTonViewModel = SingleTonViewModel(),
    authViewModel: AuthViewModel = AuthViewModel(), //todo: 언젠가는 DI 적용
    couponViewModel: CouponViewModel = CouponViewModel(),
    marketViewModel: MarketViewModel = MarketViewModel(),
    tempMarketViewModel: TempMarketViewModel = TempMarketViewModel()
) {
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
                authViewModel = authViewModel
            )
        }
        navigation(route = Page.Main.name, startDestination = Page.Home.name) {
            composable(Page.Home.name) {
                HomePage(
                    navController = navController,
                    couponViewModel = couponViewModel
                )
            }
            composable(Page.Like.name) {
                LikePage(
                    navController = navController,
                    authViewModel = authViewModel,
                    tempMarketViewModel = tempMarketViewModel
                )
            }
            composable(Page.Map.name) {
                MapPage(
                    navController = navController,
                    marketViewModel = marketViewModel
                )
            }
            composable(Page.My.name) {
                MyPage(
                    navController = navController,
                    authViewModel = authViewModel,
                    marketViewModel = marketViewModel
                )
            }
            composable(Page.CouponHam.name) {
                ReceivedCouponsScreen(
                    navController = navController,
                    couponViewModel = couponViewModel
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
                singleTon = singlethone,
                authViewModel = authViewModel
            )
        }
        composable(route = "${Page.MarketListPage.name}/{category}") {
            it.arguments?.getString("category")?.let { category ->
                MarketListPage(
                    nacController = navController,
                    marketViewModel = marketViewModel,
                    _category = LargeCategory.valueOf(category)
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
                    couponViewModel = couponViewModel,
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