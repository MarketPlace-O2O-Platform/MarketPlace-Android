package dev.kichan.marketplace.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import java.lang.Exception

@Composable
fun KakaoMap(
    modifier: Modifier = Modifier,
    latitude: Float,
    longitude: Float
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            mapView.apply {
                this.start(
                    object : MapLifeCycleCallback() {
                        override fun onMapDestroy() { // 지도가 파괴됐을 떄
                        }

                        override fun onMapError(error: Exception?) { // 지도에서 에러가 발생했을 때
                            Log.e("KakaoMapView", error!!.message.toString())
                        }
                    },
                    object : KakaoMapReadyCallback() {
                        override fun onMapReady(kakaoMap: KakaoMap) { // 지도가 추가됐을 때

                        }
                    }
                )
            }
        }
    )
}

@Preview
@Composable
private fun KakaoMapPreview() {
    MarketPlaceTheme {
        KakaoMap(latitude = 0.0f, longitude = 0.0f)
    }
}