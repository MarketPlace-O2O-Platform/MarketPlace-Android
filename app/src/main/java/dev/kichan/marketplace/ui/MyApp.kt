package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui

import LargeCategory
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.AuthViewModel
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.page.EventListPage
import dev.kichan.marketplace.ui.page.HomePage
import dev.kichan.marketplace.ui.page.LikePage
import dev.kichan.marketplace.ui.page.MapPage
import dev.kichan.marketplace.ui.page.MyPage
import dev.kichan.marketplace.ui.page.CategoryEventListPage
import dev.kichan.marketplace.ui.page.LoginPage
import dev.kichan.marketplace.ui.page.CouponPage
import dev.kichan.marketplace.SingleTonViewModel
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun MyApp(authViewModel: AuthViewModel) {

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
                    viewModel = authViewModel
                )
            }
            composable(Page.Like.name) { LikePage(navController = navController) }
            composable(Page.Map.name) { MapPage(navController = navController) }
            composable(Page.My.name) {
                MyPage(
                    navController = navController,
                    viewModel = authViewModel
                )
            }
            composable(Page.CouponHam.name) { CouponPage(navController = navController) }
            composable("${Page.EventDetail.name}/{id}") {
                it.arguments?.getString("id")?.let { id ->
                    MarketDetailPage(navController, id.toLong())
                }
            }
        }
        composable(route = Page.Login.name) {
            LoginPage(navController = navController, authViewModel = authViewModel)
        }

        composable("${Page.CategoryEventList.name}/{category}") {
            it.arguments?.getString("category")?.let { category ->
                CategoryEventListPage(
                    navController = navController,
                    viewModel = authViewModel,
                    category = LargeCategory.valueOf(category)
                )
            }
        }

        composable("${Page.EventList.name}/{title}") {
            it.arguments?.getString("title")?.let { title ->
                EventListPage(navController = navController, title = title)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MarketPlaceTheme {
        MyApp(AuthViewModel())
    }
}