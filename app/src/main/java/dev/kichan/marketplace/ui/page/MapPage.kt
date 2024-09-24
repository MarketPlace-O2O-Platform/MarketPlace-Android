package dev.kichan.marketplace.ui.page

import LargeCategory
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kakao.vectormap.LatLng
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.coupon.Coupon
import dev.kichan.marketplace.model.data.kakao.KakaoLocal
import dev.kichan.marketplace.model.data.kakao.adress.Address
import dev.kichan.marketplace.model.data.kakao.local.Place
import dev.kichan.marketplace.model.service.KakaoLocalService
import dev.kichan.marketplace.ui.component.CategoryTap
import dev.kichan.marketplace.ui.component.CouponCard
import dev.kichan.marketplace.ui.component.KakaoMap
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

@Composable
fun MapPage(navController: NavController) {
    var placeDate by remember { mutableStateOf<KakaoLocal<Place>?>(null) }
    var addressData by remember { mutableStateOf<List<Address?>>(listOf()) }
    var loadtime by remember { mutableStateOf(0L) }
    var isLoading by remember { mutableStateOf(false) }
    var page by remember { mutableStateOf(1) }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.HalfExpanded,
        confirmValueChange = { true })
    val sheetScope = rememberCoroutineScope()

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

    ModalBottomSheetLayout(
        sheetState = sheetState, // 바텀 시트 상태
        sheetContent = {
            SheetContent(
                modifier = Modifier.fillMaxHeight(0.8f)
            ) // 바텀 시트의 내용물
        },
        sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp), // 바텀시트 둥근 모양
        sheetBackgroundColor = Color(0xffFAFAFA), // 바텀시트 배경 색
        scrimColor = Color.Unspecified, // 바컴 시트 뒤에 투명한 배경색, 지금은 투명으로,
        sheetElevation = 3.dp // 바텀시트 그림자
    ) {
        // 바텀 시트뒤 배경
        SheetBack(
            mapPosition = inu,
            placeDate = placeDate,
            sheetState = sheetState,
            sheetScope = sheetScope,
            onOpenBottomSheet = { sheetScope.launch { sheetState.show() } }
        )
    }
}

@Composable
fun SheetContent(modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 20.dp),
        modifier = modifier
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(34.dp)
                        .height(5.dp)
                        .background(Color(0xffc7c7c7), RoundedCornerShape(12.dp))
                ) {}
            }
        }

        item { Spacer(modifier = Modifier.height(21.dp)) }

        items(10) {
            CouponCard(
                coupon = Coupon(
                    id = 0,
                    marketId = 0,
                    name = "커트 2,000원 할인 $it",
                    description = null,
                    deadline = LocalDate.of(2024, 10, 31),
                    count = 0,
                    isHidden = false,
                    isDeleted = false,
                    createdAt = LocalDate.now(),
                    modifiedAt = null

                ),
                imageUrl = "https://via.placeholder.com/150" //임시
            )

            // 쿠폰 아이템 사이에 있는 그거
            if (it != 9) { // 마지막이 아니면 보임
                Spacer(modifier = Modifier.height(20.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color(0xfff4f4f4))
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SheetContentPreview() {
    MarketPlaceTheme {
        SheetContent()
    }
}

@Composable
fun SheetBack(
    mapPosition: LatLng,
    placeDate: KakaoLocal<Place>?,
    sheetState: ModalBottomSheetState,
    sheetScope: CoroutineScope,
    onOpenBottomSheet : () -> Unit
) {
    var selectedCategory by remember { mutableStateOf(LargeCategory.Food) }

    Box {
        KakaoMap(
            position = mapPosition,
            marker = placeDate?.documents?.map {
                LatLng.from(
                    it.y.toDouble(),
                    it.x.toDouble()
                )
            } ?: listOf(
                LatLng.from(
                    37.376651978907326,
                    126.63425891507083,
                )
            ),
            onMarketClick = onOpenBottomSheet
        )

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
            Button(onClick = { sheetScope.launch { sheetState.show() } }) {
                Text(text = "열기")
            }

            Button(onClick = { sheetScope.launch { sheetState.show() } }) {
                Text(text = "열기")
            }
//                Button(onClick = {
//                    page -= 1
//                    getData()
//                }) {
//                    Text(text = "이전 페이지")
//                }
//
//                Button(onClick = { getData() }) {
//                    Text(text = "데이터 로드")
//                }
//
//                Button(onClick = {
//                    page += 1
//                    getData()
//                }) {
//                    Text(text = "다음 페이지")
//                }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MapPagePreview() {
    MarketPlaceTheme {
        MapPage(rememberNavController())
    }
}