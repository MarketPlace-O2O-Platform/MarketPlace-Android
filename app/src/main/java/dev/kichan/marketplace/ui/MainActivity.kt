package dev.kichan.marketplace.ui

import LoginPage
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
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.MyApp
import dev.kichan.marketplace.ui.page.CouponPage
import dev.kichan.marketplace.ui.page.GoogleMapTestPage
import dev.kichan.marketplace.ui.page.HomePage
import dev.kichan.marketplace.ui.page.LikePage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

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
//                LoginPage(navController = rememberNavController())
                MyApp()
            }
        }
    }
}