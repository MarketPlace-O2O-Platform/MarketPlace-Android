package dev.kichan.marketplace.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapView
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.kakao.KakaoLocal
import dev.kichan.marketplace.model.data.kakao.adress.Address
import dev.kichan.marketplace.model.data.kakao.local.Place
import dev.kichan.marketplace.model.service.KakaoLocalService
import dev.kichan.marketplace.ui.component.KakaoMap
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MapPage() {
    var placeDate by remember { mutableStateOf<KakaoLocal<Place>?>(null) }
    var addressData by remember { mutableStateOf<List<Address?>>(listOf()) }
    var loadtime by remember { mutableStateOf(0L) }
    var isLoading by remember { mutableStateOf(false) }
    var page by remember { mutableStateOf(1) }

    val getData = {
        val retrofit = NetworkModule().provideRetrofit("https://dapi.kakao.com/")
        val service = retrofit.create(KakaoLocalService::class.java)

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
                val addressRes = service.getAddress(query = it.address_name).body()!!.documents.getOrNull(0)
                if(addressRes == null) {
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

    val inu = LatLng.from(
        37.376651978907326,
        126.63425891507083,
    )

    Box {
        KakaoMap(position = inu, marker = placeDate?.documents?.map { LatLng.from(it.y.toDouble(), it.x.toDouble()) } ?: listOf(inu))

        Row(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Button(onClick = {
                page -= 1
                getData()
            }) {
                Text(text = "이전 페이지")
            }

            Button(onClick = { getData() }) {
                Text(text = "데이터 로드")
            }

            Button(onClick = {
                page += 1
                getData()
            }) {
                Text(text = "다음 페이지")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapPagePreview() {
    MarketPlaceTheme {
        MapPage()
    }
}