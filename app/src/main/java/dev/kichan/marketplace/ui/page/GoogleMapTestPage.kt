package dev.kichan.marketplace.ui.page

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.dto.kakao.KakaoLocal
import dev.kichan.marketplace.model.dto.kakao.adress.Address
import dev.kichan.marketplace.model.dto.kakao.local.Place
import dev.kichan.marketplace.model.service.KakaoLocalService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun GoogleMapTestPage(navController: NavHostController) {
    var isLoaded by remember { mutableStateOf(false) }
    var placeDate by remember { mutableStateOf<KakaoLocal<Place>?>(null) }
    var addressData by remember { mutableStateOf<List<Address?>>(listOf()) }
    var loadtime by remember { mutableStateOf(0L) }
    var isLoading by remember { mutableStateOf(false) }
    var page by remember { mutableStateOf(1) }

    val getData = {
//        val retrofit = NetworkModule.getService(KakaoLocalService::class.java)
        val service = NetworkModule.getService(KakaoLocalService::class.java)

        isLoading = true

        CoroutineScope(Dispatchers.IO).launch {
            val time1 = System.currentTimeMillis()

            val res = service.searchKeyword(
                query = "음식점",
                x = "126.63425891507083",
                y = "37.376651978907326",
                radius = 1000,
                page = page,
            )
            val addressList = res.body()!!.documents.map {
                val addressRes =
                    service.getAddress(query = it.address_name).body()!!.documents.getOrNull(0)
                if (addressRes == null) {
                    Log.d("address", it.toString())
                }

                addressRes
            }

            val time2 = System.currentTimeMillis()

            withContext(Dispatchers.Main) {
                loadtime = time2 - time1
                isLoading = false
                addressData = addressList
                placeDate = res.body()
            }
        }
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                37.376651978907326,
                126.63425891507083,
            ), 12f
        )
    }

    val c = LocalContext.current
    data class BitmapParameters(
        @DrawableRes val id: Int,
        @ColorInt val iconColor: Int,
        @ColorInt val backgroundColor: Int? = null,
        val backgroundAlpha: Int = 168,
        val padding: Int = 16,
    )

    val bitmapCache = mutableMapOf<BitmapParameters, BitmapDescriptor>()
    fun createBitmapDescriptor(
        context: Context,
        parameters: BitmapParameters
    ): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(
            context.resources,
            parameters.id,
            null
        ) ?: return run {
            BitmapDescriptorFactory.defaultMarker()
        }

        val padding = if (parameters.backgroundColor != null) parameters.padding else 0
        val halfPadding = padding / 2

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth + padding,
            vectorDrawable.intrinsicHeight + padding,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(
            halfPadding,
            halfPadding,
            canvas.width - halfPadding,
            canvas.height - halfPadding
        )
        DrawableCompat.setTint(vectorDrawable, parameters.iconColor)

        if (parameters.backgroundColor != null) {
            val fillPaint = Paint().apply {
                style = Paint.Style.FILL
                color = parameters.backgroundColor
                alpha = parameters.backgroundAlpha
            }

            val strokePaint = Paint().apply {
                style = Paint.Style.STROKE
                color = parameters.backgroundColor
                strokeWidth = 3f
            }

            canvas.drawCircle(
                canvas.width / 2.0f,
                canvas.height / 2.0f,
                canvas.width.toFloat() / 2,
                fillPaint
            )
            canvas.drawCircle(
                canvas.width / 2.0f,
                canvas.height / 2.0f,
                (canvas.width.toFloat() / 2) - 3,
                strokePaint
            )
        }
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap).also { bitmapCache[parameters] = it }
    }
    fun vectorToBitmap(context: Context, parameters: BitmapParameters): BitmapDescriptor {
        return bitmapCache[parameters] ?: createBitmapDescriptor(context, parameters)
    }

    var clickMarker by remember { mutableStateOf(listOf<LatLng>()) }

    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                onMapLoaded = { isLoaded = true },
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = true,
                ),
                onMapClick = {
                    clickMarker = clickMarker + listOf(it)
                }
            ) {

                Polyline(points = clickMarker)
            }

            Button(onClick = {
                getData()
            }) {
                Text(text = "데이터 가져오기")
            }
//            Text(text = if (isLoaded) "완료" else "로딩중", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleMapTestPagePreview() {
    MarketPlaceTheme {
        GoogleMapTestPage(rememberNavController())
    }
}
