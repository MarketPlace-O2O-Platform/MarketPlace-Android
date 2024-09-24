package dev.kichan.marketplace.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.kakao.vectormap.KakaoMapSdk
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.ui.component.BottomNavigationBar
import dev.kichan.marketplace.ui.page.HomePage
import dev.kichan.marketplace.ui.page.LikePage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.page.MapPage
import dev.kichan.marketplace.ui.page.MyPage
import dev.kichan.marketplace.ui.page.PopularEventPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Key Hash 가져오는 코드
//        val keyHash = Utility.getKeyHash(this)
//        Log.i("GlobalApplication", keyHash)

        KakaoMapSdk.init(this, BuildConfig.KAKAO_NATIVE_API_KEY)

        setContent {
            MarketPlaceTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val bottomNavItem = listOf(
        Page.Home to Icons.Filled.Home,
        Page.Like to Icons.Filled.ShoppingCart,
        Page.Map to Icons.Filled.Place,
        Page.My to Icons.Filled.Person,
    )

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, pageList = bottomNavItem) }
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
            composable(Page.PopularEvent.name) { PopularEventPage(navController = navController) }
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

//fun main() {
//    val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//    for(i in info.signatures) {
//        val md: MessageDigest = MessageDigest.getInstance("SHA")
//        md.update(i.toByteArray())
//
//        val something = String(Base64.encode(md.digest(), 0)!!)
//        Log.e("Debug key", something)
//    }
//}