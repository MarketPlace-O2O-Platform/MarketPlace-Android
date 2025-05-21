package dev.kichan.marketplace.ui.page

import Carbon_bookmark
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.model.service.MarketService
import dev.kichan.marketplace.ui.theme.PretendardFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import dev.kichan.marketplace.ui.component.atoms.DetailCoupon
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes


import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import dev.kichan.marketplace.model.service.CouponService
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.viewmodel.MarketViewModel


@Composable
fun ImageSlider(imageList: List<String>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(imageList) { url ->
            AsyncImage(
                modifier = Modifier.size(280.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = "이미지",
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun KakaoMapSearchBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.search),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 12.dp)
                .size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = buildAnnotatedString {
                append("카카오맵에서 ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("콜드케이스 트리플")
                }
                append(" 인하대점 검색")
            },
            fontSize = 14.sp,
            color = Color(0xFF545454),
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun MarketDetailPage(
    navController: NavHostController,
    marketViewModel: MarketViewModel,
    id: Long,
) {
    val state = marketViewModel.marketDetailPageUiState

    LaunchedEffect(Unit) {
        marketViewModel.getMarket(id)
        marketViewModel.getMarketCoupon(id)
    }

    if(state.marketData == null) return

    // 쿠폰 받기 다이얼로그 상태 변수
    Scaffold(
        topBar = {
            NavAppBar("", Color.White) { navController.popBackStack() }
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(
                PaddingValues(
                    top = 0.dp,
                    bottom = it.calculateBottomPadding(),
                    start = it.calculateStartPadding(LayoutDirection.Ltr),
                    end = it.calculateEndPadding(LayoutDirection.Rtl)
                )
            )
        ) {
            item {
                ImageSlider(state.marketData.imageResList.map { NetworkModule.getImage(it.name) })
            }
//            item { MainInfo(data) }
//            item {
//                HorizontalDivider(
//                    Modifier
//                        .height(8.dp)
//                        .background(Color(0xffeeeee))
//                )
//            }
//            // 쿠폰 섹션: "이벤트 쿠폰" 타이틀과 함께 DetailCoupon 표시
//            item {
//                Column(
//                    modifier = Modifier.padding(20.dp)
//                ) {
//                    Text(
//                        "이벤트 쿠폰",
//                        fontFamily = PretendardFamily,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 14.sp
//                    )
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    // ✅ coupons 리스트가 비어있지 않은 경우에만 LazyRow로 쿠폰들 보여주기
//                    if (coupons.isNotEmpty()) {
//                        LazyRow(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp),
//                            contentPadding = PaddingValues(horizontal = 8.dp)
//                        ) {
//                            items(coupons) { coupon ->
//                                // 화면 너비만큼 쿠폰 하나가 차지하도록
//                                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
//
//                                DetailCoupon(
//                                    coupon = coupon,
//                                    modifier = Modifier.width(screenWidth),
//                                    onClick = {
//                                        selectedCoupon = coupon
//                                        isCouponDialogShow = true
//                                    }
//                                )
//                            }
//                        }
//                    } else {
//                        // ✅ 데이터가 없을 때는 "쿠폰이 없습니다" 메시지 (옵션)
//                        Text(
//                            "사용 가능한 쿠폰이 없습니다.",
//                            fontFamily = PretendardFamily,
//                            fontSize = 14.sp,
//                            color = Color.Gray
//                        )
//                    }
//                }
//            }
//
//            item {
//                HorizontalDivider(
//                    Modifier
//                        .height(8.dp)
//                        .background(Color(0xffeeeee))
//                )
//            }
//            item { BusinessInfo(data) }
//            item { KakaoMapSearchBox() }
//        }
    }

//    // 쿠폰 받기 다이얼로그
//    if (isCouponDialogShow && selectedCoupon != null) {
//        Dialog(onDismissRequest = { isCouponDialogShow = false }) {
//            Column(
//                modifier = Modifier
//                    .width(320.dp)
//                    .background(Color.White, shape = RoundedCornerShape(12.dp))
//                    .padding(horizontal = 24.dp, vertical = 20.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = "${selectedCoupon?.couponName} 쿠폰을 받으시겠습니까?",
//                    fontSize = 20.sp,
//                    lineHeight = 30.sp,
//                    fontWeight = FontWeight(700),
//                    textAlign = TextAlign.Center,
//                    fontFamily = PretendardFamily
//                )
//                Spacer(modifier = Modifier.height(20.dp))
//                Button(
//                    onClick = {
//                        // 실제 쿠폰 받기 API 호출 등 처리
//                        isCouponDialogShow = false
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(48.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
//                    shape = RoundedCornerShape(4.dp)
//                ) {
//                    Text(
//                        text = "받기",
//                        fontSize = 12.sp,
//                        lineHeight = 16.8.sp,
//                        color = Color.White,
//                        fontWeight = FontWeight(500),
//                        textAlign = TextAlign.Center,
//                        fontFamily = PretendardFamily
//                    )
//                }
//                Spacer(modifier = Modifier.height(12.dp))
//                OutlinedButton(
//                    onClick = { isCouponDialogShow = false },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(48.dp),
//                    colors = ButtonDefaults.outlinedButtonColors(
//                        containerColor = Color.White,
//                        contentColor = Color.Black
//                    ),
//                    border = ButtonDefaults.outlinedButtonBorder,
//                    shape = RoundedCornerShape(4.dp)
//                ) {
//                    Text(
//                        text = "취소",
//                        fontSize = 12.sp,
//                        lineHeight = 16.8.sp,
//                        textAlign = TextAlign.Center,
//                        fontWeight = FontWeight(500),
//                        fontFamily = PretendardFamily
//                    )
//                }
//            }
//        }
    }
}

//@Composable
//private fun BusinessInfo(data: MutableState<MarketDetailRes?>) {
//    Column(
//        modifier = Modifier.padding(20.dp)
//    ) {
//        Text(
//            text = "영업정보",
//            fontFamily = PretendardFamily,
//            fontWeight = FontWeight.SemiBold,
//            fontSize = 14.sp
//        )
//        Spacer(modifier = Modifier.height(12.dp))
//        BusinessInfoRow("시간", data.value!!.operationHours)
//        BusinessInfoRow("휴무일", data.value!!.closedDays)
//        BusinessInfoRow("매장 전화번호", data.value!!.phoneNumber)
//        BusinessInfoRow("주소", data.value!!.address)
//    }
//}
//
//@Composable
//private fun MainInfo(data: MutableState<MarketDetailRes?>) {
//    Row(
//        modifier = Modifier.padding(20.dp)
//    ) {
//        Column(
//            modifier = Modifier.weight(1f)
//        ) {
//            Text(
//                text = data.value!!.name,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.SemiBold,
//                fontFamily = PretendardFamily
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = data.value!!.description,
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Medium,
//                fontFamily = PretendardFamily,
//                color = Color(0xff7D7D7D)
//            )
//        }
//        Spacer(modifier = Modifier.width(12.dp))
//        IconButton({ /* 즐겨찾기 처리 */ }) {
//            Icon(imageVector = Carbon_bookmark, contentDescription = null)
//        }
//    }
//}
//
//@Composable
//fun BusinessInfoRow(label: String, value: String) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        verticalAlignment = Alignment.Top
//    ) {
//        Text(
//            text = label,
//            color = Color(0xFF868686),
//            fontSize = 14.sp,
//            modifier = Modifier.width(100.dp)
//        )
//        Column {
//            Text(
//                text = value,
//                color = Color(0xFF5E5E5E),
//                fontSize = 14.sp
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DetailPagePreview() {
//    MarketDetailPage(rememberNavController(), 12121L)
//}
