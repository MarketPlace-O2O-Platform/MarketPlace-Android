package dev.kichan.marketplace.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.github.javafaker.Faker
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.util.Utility
import com.kakao.vectormap.KakaoMapSdk
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.SingleTonViewModel
import dev.kichan.marketplace.viewmodel.TempMarketViewModel
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.viewmodel.AuthViewModel
import dev.kichan.marketplace.viewmodel.CouponViewModel
import dev.kichan.marketplace.viewmodel.MarketViewModel
import java.util.Locale


val faker = Faker(Locale.KOREAN)

class MainActivity : ComponentActivity() {
    private val singleTon: SingleTonViewModel by viewModels()
    private val couponViewModel : CouponViewModel by viewModels()
    private val marketViewModel : MarketViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val tempMarketViewModel: TempMarketViewModel by viewModels()

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result
                authViewModel.saveFcmToken(token)
                Log.d("FCM", "FCM token: $token")
            }
    }

    //todo: 권한 요청 로직 변경
    //todo: 함수명 변경
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 이상
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false) // WindowInsets 활성화

        // Key Hash 가져오는 코드
        val keyHash = Utility.getKeyHash(this)
        Log.i("GlobalApplication", keyHash)
        KakaoMapSdk.init(this, BuildConfig.KAKAO_NATIVE_API_KEY)

//        val intent = Intent(this, MyFirebaseMessagingService::class.java)
//        startService(intent)

        getFCMToken()

        setContent {
            MarketPlaceTheme {
                MyApp(
                    singleTon,
                    authViewModel = authViewModel,
                    couponViewModel = couponViewModel,
                    marketViewModel = marketViewModel,
                    tempMarketViewModel = tempMarketViewModel
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestNotificationPermission()
    }
}