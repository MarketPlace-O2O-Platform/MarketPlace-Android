package dev.kichan.marketplace.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.page.CategoryPage
import dev.kichan.marketplace.ui.page.HomePage
import dev.kichan.marketplace.ui.page.MapPage
import dev.kichan.marketplace.ui.page.MyPage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketPlaceTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Page.Main.name,
    ) {
        composable(Page.Main.name) {
            HomePage(navController)
        }
        composable(Page.Category.name) {
            CategoryPage(navController)
        }
        composable(Page.Map.name) {
            MapPage(navController)
        }
        composable(Page.My.name) {
            MyPage(navController)
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
