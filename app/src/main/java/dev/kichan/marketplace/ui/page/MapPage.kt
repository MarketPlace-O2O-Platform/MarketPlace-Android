package dev.kichan.marketplace.ui.page

import LargeCategory
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kakao.vectormap.LatLng
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.kakao.KakaoLocal
import dev.kichan.marketplace.model.data.kakao.adress.Address
import dev.kichan.marketplace.model.data.kakao.local.Place
import dev.kichan.marketplace.model.service.KakaoLocalService
import dev.kichan.marketplace.ui.component.KakaoMap
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily
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

    var selectedCategory by remember { mutableStateOf(LargeCategory.Food) }

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

    val inu = LatLng.from(
        37.376651978907326,
        126.63425891507083,
    )

    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            KakaoMap(
                position = inu,
                marker = placeDate?.documents?.map { LatLng.from(it.y.toDouble(), it.x.toDouble()) }
                    ?: listOf(inu))

            CategoryTap(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                selectedCategory = selectedCategory,
                onSelected = { selectedCategory = it }
            )


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
}

@Composable
fun CategoryTap(
    modifier: Modifier = Modifier,
    selectedCategory: LargeCategory,
    onSelected: (LargeCategory) -> Unit
) {
    val itemStyle = TextStyle(
        fontFamily = PretendardFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    )
    LazyRow(
        modifier = modifier
            .background(Color.White)
            .padding(bottom = 5.dp)
    ) {
        LargeCategory.entries.map {
            item {
                Surface(
                    modifier = Modifier
                        .width(62.dp)
                        .padding(vertical = 8.dp)
                        .clickable { onSelected(it) },
                ) {
                    Text(
                        text = it.nameKo,
                        textAlign = TextAlign.Center,
                        style = itemStyle.copy(
                            color = if (selectedCategory == it) Color(0xff121212) else Color(
                                0xff7D7D7D
                            )
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CateroryTapPreview() {
    MarketPlaceTheme {
        CategoryTap(
            selectedCategory = LargeCategory.Food,
            onSelected = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MapPagePreview() {
    MarketPlaceTheme {
        MapPage()
    }
}