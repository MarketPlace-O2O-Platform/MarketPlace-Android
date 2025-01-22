package dev.kichan.marketplace.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.kakao.vectormap.KakaoMapSdk
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.AuthViewModel
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.MyApp
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

class MainActivity : ComponentActivity() {
    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false) // WindowInsets 활성화

        // Key Hash 가져오는 코드
//        val keyHash = Utility.getKeyHash(this)
//        Log.i("GlobalApplication", keyHash)

        KakaoMapSdk.init(this, BuildConfig.KAKAO_NATIVE_API_KEY)

        setContent {
            MarketPlaceTheme {
                MyApp(authViewModel = authViewModel)
            }
        }
    }
}