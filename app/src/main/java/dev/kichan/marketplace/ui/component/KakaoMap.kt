package dev.kichan.marketplace.ui.component

import android.R
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme


@Composable
fun KakaoMap(
    modifier: Modifier = Modifier,
    position : LatLng,
    marker : List<LatLng> = listOf()
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
                            kakaoMap.moveCamera(
                                CameraUpdateFactory.newCenterPosition(position)
                            )

                            marker.forEach {
                                val options = LabelOptions.from(it)
                                val layer = kakaoMap.labelManager!!.layer
                                val label = layer!!.addLabel(options)
                            }
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
    val inu = LatLng.from(
        37.376651978907326,
        126.63425891507083,
    )
    MarketPlaceTheme {
        KakaoMap(position = inu)
    }
}