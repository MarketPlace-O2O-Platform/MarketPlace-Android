package dev.kichan.marketplace.ui.page

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.LayoutDirection
import coil3.compose.AsyncImage
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketDetailRes
import dev.kichan.marketplace.ui.theme.PretendardFamily
import androidx.compose.material3.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import dev.kichan.marketplace.ui.CouponDownloadCheckDialog
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.viewmodel.MarketViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun MarketDetailPage(
    navController: NavHostController,
    marketViewModel: MarketViewModel,
    id: Long,
) {
    val state = marketViewModel.marketDetailPageUiState
    var downLoadCouponId by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) {
        marketViewModel.getMarket(id)
        marketViewModel.getMarketCoupon(id)
    }

    if (state.marketData == null) return

    // 쿠폰 받기 다이얼로그 상태 변수
    Scaffold(
        topBar = {
            NavAppBar("", Color.White) { navController.popBackStack() }
        },
    ) {
        if(downLoadCouponId != null) {
            CouponDownloadCheckDialog(
                onDismiss = { downLoadCouponId = null },
                onAccept = {  }
            )
        }

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
            item { MainInfo(state.marketData) }
            item {
                HorizontalDivider(
                    Modifier
                        .height(8.dp)
                        .background(Color(0xffeeeee))
                )
            }

            item {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "이벤트 쿠폰",
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    if (state.couponList.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            items(state.couponList) { coupon ->
                                val screenWidth = LocalConfiguration.current.screenWidthDp.dp

                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                val parsedDate =
                                    LocalDate.parse(coupon.deadline.substring(0, 10), formatter)
                                val expireDate =
                                    parsedDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일까지"))

                                TicketCoupon(
                                    title = coupon.name,
                                    expireDate = expireDate,
                                    width = screenWidth - PAGE_HORIZONTAL_PADDING * 2 - 40.dp,
                                    onClick = { downLoadCouponId = coupon.id },
                                )
                            }
                        }
                    } else {
                        Text(
                            "매장에 등록된 쿠폰이 없습니다.",
                            fontFamily = PretendardFamily,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            item {
                HorizontalDivider(
                    Modifier
                        .height(8.dp)
                        .background(Color(0xffeeeee))
                )
            }
            item { BusinessInfo(state.marketData) }
            item { KakaoMapSearchBox(state.marketData.name) }
        }
    }
}

@Composable
fun ImageSlider(imageList: List<String>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(imageList) { url ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                model = NetworkModule.getImageModel(LocalContext.current, url),
                contentDescription = "이미지",
                contentScale = ContentScale.Crop,
            )
        }
    }
}


@Composable
private fun MainInfo(data: MarketDetailRes) {
    Row(
        modifier = Modifier.padding(20.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = data.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PretendardFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data.description,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = PretendardFamily,
                color = Color(0xff7D7D7D)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        var isBook by remember { mutableStateOf(false) }

        //todo: 즐겨찾기 처리
        IconButton(
            onClick = { isBook = !isBook }
        ) {
            Icon(
                painter = painterResource(if (isBook) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark),
                contentDescription = null
            )
        }
    }
}

@Composable
fun TicketCoupon(
    title: String,
    expireDate: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp,
    cutRadius: Dp = 10.dp,
    lineFraction: Float = 0.75f,
) {
    val dark = Color(0xFF2B2B2B)
    val white = Color.White
    val downloadWidth = 90.dp

    val ticketShape = remember(cutRadius, lineFraction) {
        object : Shape {
            override fun createOutline(
                size: Size,
                layoutDirection: LayoutDirection,
                density: Density
            ): Outline {
                val r = with(density) { cutRadius.toPx() }
                val downloadWidthPx = with(density) { downloadWidth.toPx() }
                val x = size.width - downloadWidthPx

                val p = Path().apply {
                    addRect(Rect(0f, 0f, size.width, size.height))

                    addOval(Rect(x - r, -r, x + r, r))
                    addOval(Rect(x - r, size.height - r, x + r, size.height + r))

                    fillType = PathFillType.EvenOdd
                }
                return Outline.Generic(p)
            }
        }
    }

    Row(
        modifier
            .width(width)
            .height(88.dp)
            .clip(ticketShape)
            .background(dark)
            .clickable { onClick() }

            .drawBehind {
                val dashW = 4.dp.toPx()
                val dashGap = 6.dp.toPx()
                val stroke = 2.dp.toPx()
                val downloadWidthPx = downloadWidth.toPx()
                val x = size.width - downloadWidthPx
                drawLine(
                    color = white,
                    start = Offset(x, 0f + cutRadius.toPx()),
                    end = Offset(x, size.height - cutRadius.toPx()),
                    strokeWidth = stroke,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashW, dashGap))
                )
            }
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight()
                .padding(start = 20.dp, end = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = white,
                fontFamily = PretendardFamily,
                maxLines = 1
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = expireDate,
                style = MaterialTheme.typography.bodyMedium.copy(color = white)
            )
        }

        Column(
            modifier = Modifier
                .width(downloadWidth)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_download),
                contentDescription = "download",
                modifier = Modifier.size(20.dp),
                tint = white
            )
            Spacer(Modifier.height(8.dp))
            Text("쿠폰받기", color = white)
        }
    }
}

@Composable
private fun BusinessInfo(data: MarketDetailRes) {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "영업정보",
            fontFamily = PretendardFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        BusinessInfoRow("시간", data.operationHours)
        BusinessInfoRow("휴무일", data.closedDays)
        BusinessInfoRow("매장 전화번호", data.phoneNumber)
        BusinessInfoRow("주소", data.address)
    }
}

@Composable
fun BusinessInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = Color(0xFF868686),
            fontSize = 14.sp,
            modifier = Modifier.width(100.dp)
        )

        Text(
            text = value,
            color = Color(0xFF5E5E5E),
            fontSize = 14.sp
        )
    }
}

@Composable
fun KakaoMapSearchBox(marketName: String) {
    val context = LocalContext.current

    val onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://search?q=${marketName}"))
        context.startActivity(intent)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
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
                    append(marketName)
                }
                append(" 검색")
            },
            fontSize = 14.sp,
            color = Color(0xFF545454),
            fontWeight = FontWeight.Normal
        )
    }
}