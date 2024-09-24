package dev.kichan.marketplace.ui.component

import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme


@Composable
fun KakaoMap(
    modifier: Modifier = Modifier,
    position: LatLng,
    marker: List<LatLng> = listOf(),
    onMarketClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    var kakaoMapState by remember { mutableStateOf<KakaoMap?>(null) }
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            mapView.parent?.let { parent ->
//                Log.d("marker")
                (parent as ViewGroup).removeView(mapView)
            }

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
                            kakaoMapState = kakaoMap
                            kakaoMap.moveCamera(
                                CameraUpdateFactory.newCenterPosition(position)
                            )

                            addMarkers(kakaoMap, marker, onMarketClick)
                        }
                    }
                )
            }
        },
        update = { view ->
            kakaoMapState?.let {
                addMarkers(it, marker, onMarketClick)
            }
        }
    )
}

fun addMarkers(kakaoMap: KakaoMap, markers: List<LatLng>, onMarketClick: () -> Unit) {
    kakaoMap.labelManager?.clearAll()
    markers.forEach { marker ->
        val style = kakaoMap.labelManager?.addLabelStyles(
            LabelStyles.from(LabelStyle.from(R.drawable.image)),
        )
        val options = LabelOptions.from(marker).setStyles(style).setClickable(true)
        val layer = kakaoMap.labelManager?.layer
        val lable = layer?.addLabel(options)
    }
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