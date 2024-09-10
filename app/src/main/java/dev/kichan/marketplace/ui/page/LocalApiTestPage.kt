package dev.kichan.marketplace.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.kakao.KakaoLocal
import dev.kichan.marketplace.model.data.kakao.adress.Address
import dev.kichan.marketplace.model.data.kakao.local.Place
import dev.kichan.marketplace.model.service.KakaoLocalService
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LocalApiTestPage(navController: NavController) {
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


//    KakaoMap(modifier = Modifier.fillMaxSize(), latitude = 0.0f, longitude = 0.0f)
    Scaffold {
        Column(Modifier.padding(it)) {
            Button(onClick = { getData() }) {
                Text(text = "가져오기")
            }
            Row {
                Button(onClick = {
                    page -= 1
                    getData()
                }) {
                    Text(text = "이전 페이지")
                }

                Button(onClick = {
                    page += 1
                    getData()
                }) {
                    Text(text = "다음 페이지")
                }

                if(isLoading) Text(text = "로딩중", color= Color.White)
            }
            if (placeDate != null) {
                Text(text = "${page}페이지", color = Color.White)
                Text(
                    text = "총 ${placeDate?.documents?.size}개 / ${addressData.size}",
                    color = Color.White
                )
                Text(text = "총 ${loadtime}ms 소요 (1개당 ${loadtime / placeDate!!.documents.size}ms)", color = Color.White)
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(placeDate!!.documents) {
                        Text(text = "${it.place_name} / ${it.address_name}", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocalApiTestPagePreview() {
    MarketPlaceTheme {
        LocalApiTestPage(rememberNavController())
    }
}