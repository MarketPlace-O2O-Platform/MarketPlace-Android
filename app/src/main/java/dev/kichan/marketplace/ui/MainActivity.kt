package dev.kichan.marketplace.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.MapView
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.ui.page.CategoryPage
import dev.kichan.marketplace.ui.page.HomePage
import dev.kichan.marketplace.ui.page.LocalApiTestPage
import dev.kichan.marketplace.ui.page.MapPage
import dev.kichan.marketplace.ui.page.MyPage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import com.kakao.sdk.common.util.Utility
import dev.kichan.marketplace.ui.page.MainPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Key Hash 가져오는 코드
        val keyHash = Utility.getKeyHash(this)
        Log.i("GlobalApplication", keyHash)

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
    MainPage()
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