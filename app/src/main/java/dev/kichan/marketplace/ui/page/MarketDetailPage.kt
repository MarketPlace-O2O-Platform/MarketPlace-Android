package dev.kichan.marketplace.ui.page

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.dto.DisplayCoupon
import dev.kichan.marketplace.model.dto.MarketDetailsRes
import dev.kichan.marketplace.ui.CouponDownloadCheckDialog
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.component.atoms.PagerIndicator
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.ui.viewmodel.MarketDetailNavigationEvent
import dev.kichan.marketplace.ui.viewmodel.MarketDetailViewModel
import dev.kichan.marketplace.ui.viewmodel.MarketDetailViewModelFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun MarketDetailPage(
    navController: NavHostController,
    id: Long,
    marketDetailViewModel: MarketDetailViewModel = viewModel(
        factory = MarketDetailViewModelFactory(
            LocalContext.current.applicationContext as Application,
            id
        )
    )
) {
    val uiState by marketDetailViewModel.uiState.collectAsState()
    var downLoadCoupon by remember { mutableStateOf<DisplayCoupon?>(null) }

    LaunchedEffect(Unit) {
        marketDetailViewModel.navigationEvent.collect { event ->
            when (event) {
                is MarketDetailNavigationEvent.NavigateToMyPage -> {
                    navController.navigate(Page.My.name)
                }
            }
        }
    }

    // 페이지가 화면에 나타날 때마다 북마크 상태 새로고침
    DisposableEffect(navController.currentBackStackEntry) {
        val entry = navController.currentBackStackEntry
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                marketDetailViewModel.checkFavoriteStatus()
            }
        }
        entry?.lifecycle?.addObserver(observer)

        onDispose {
            entry?.lifecycle?.removeObserver(observer)
        }
    }

    if (uiState.marketData == null) return

    val availableCouponList = uiState.couponList.filter { coupon ->
        // 디버깅 로깅 (디버그 빌드에서만)
        if (BuildConfig.DEBUG) {
            Log.d("MarketDetailPage", "쿠폰 필터링 체크 ${coupon.couponId}: type=${coupon.couponType}, hidden=${coupon.isHidden}, issued=${coupon.isMemberIssued}")
        }

        // 공통 조건: 숨김 아님
        // 환급 쿠폰은 발급 여부와 관계없이 표시 (재발급 가능), 일반 쿠폰은 발급받지 않은 것만 표시
        (coupon.couponType == "PAYBACK" || !coupon.isMemberIssued) && !coupon.isHidden &&

        // 타입별 조건
        when (coupon.couponType) {
            "GIFT" -> {
                // 일반 쿠폰: 재고 있고, 마감일이 지나지 않음
                coupon.isAvailable && coupon.deadLine?.let { deadline ->
                    try {
                        val deadLineDate = LocalDateTime.parse(deadline.substring(0, 19))
                        deadLineDate.isAfter(LocalDateTime.now())
                    } catch (e: Exception) {
                        if (BuildConfig.DEBUG) {
                            Log.e("MarketDetailPage", "날짜 파싱 실패: 쿠폰 ${coupon.couponId}", e)
                        }
                        false
                    }
                } ?: false
            }
            "PAYBACK" -> {
                // 환급 쿠폰: 숨김이 아니고 발급받지 않았으면 표시
                // (마감일 없음, 항상 사용 가능)
                true
            }
            else -> {
                if (BuildConfig.DEBUG) {
                    Log.w("MarketDetailPage", "알 수 없는 쿠폰 타입: ${coupon.couponType}")
                }
                false
            }
        }
    }.also { filteredList ->
        // 필터링 결과 로깅 (디버그 빌드에서만)
        if (BuildConfig.DEBUG) {
            Log.d("MarketDetailPage", "쿠폰 필터링: ${uiState.couponList.size}개 → ${filteredList.size}개")
            Log.d("MarketDetailPage", "  - GIFT: ${filteredList.count { it.couponType == "GIFT" }}개")
            Log.d("MarketDetailPage", "  - PAYBACK: ${filteredList.count { it.couponType == "PAYBACK" }}개")
        }
    }

    // 쿠폰 받기 다이얼로그 상태 변수
    Scaffold(
        topBar = {
            NavAppBar("", Color.White) { navController.popBackStack() }
        },
        containerColor = Color.White
    ) {
        if (downLoadCoupon != null) {
            CouponDownloadCheckDialog(
                onDismiss = { downLoadCoupon = null },
                onAccept = {
                    if (downLoadCoupon!!.couponType == "GIFT") {
                        marketDetailViewModel.downloadGiftCoupon(downLoadCoupon!!.couponId)
                    } else {
                        marketDetailViewModel.downloadPaybackCoupon(downLoadCoupon!!.couponId)
                    }
                }
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
                ImageSlider(uiState.marketData!!.imageResList.map { img ->
                    NetworkModule.getImage(img.name)
                })
            }
            item {
                MainInfo(
                    data = uiState.marketData!!,
                    isFavorite = uiState.isFavorite,
                    onFavorite = { marketDetailViewModel.favorite(id) })
            }
            item {
                Column(
                    modifier = Modifier.padding(horizontal = PAGE_HORIZONTAL_PADDING)
                ) {
                    Text(
                        text = "이벤트 쿠폰",
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    if (availableCouponList.isNotEmpty()) {
                        val pagerState =
                            rememberPagerState(pageCount = { availableCouponList.size })
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING),
                            pageSpacing = 8.dp
                        ) {
                            val coupon = availableCouponList[it]
                            val screenWidth = LocalConfiguration.current.screenWidthDp.dp

                            val expireDate = if (coupon.couponType == "PAYBACK") {
                                "환급 쿠폰"
                            } else {
                                coupon.deadLine?.let { deadline ->
                                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                    val parsedDate = LocalDate.parse(deadline.substring(0, 10), formatter)
                                    parsedDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일까지"))
                                } ?: ""
                            }

                            TicketCoupon(
                                title = coupon.couponName,
                                expireDate = expireDate,
                                width = screenWidth - PAGE_HORIZONTAL_PADDING * 2,
                                onClick = { downLoadCoupon = coupon },
                            )
                        }
                        Column(
                            modifier = Modifier.padding(horizontal = PAGE_HORIZONTAL_PADDING),
                        ) {
                            Spacer(modifier = Modifier.height(12.dp))
                            PagerIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                pagerState = pagerState
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = availableCouponList[pagerState.targetPage].couponDescription,
                                fontFamily = PretendardFamily,
                                fontWeight = FontWeight.W400,
                                fontSize = 13.sp,
                                color = Color(0xff7D7D7D),
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.padding(horizontal = PAGE_HORIZONTAL_PADDING),
                        ) {
                            Text(
                                text = "매장에 등록된 쿠폰이 없습니다.",
                                fontFamily = PretendardFamily,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            item { BusinessInfo(uiState.marketData!!) }
            item { KakaoMapSearchBox(uiState.marketData!!.name) }
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
                model = url,
                contentDescription = "이미지",
                contentScale = ContentScale.Crop,
            )
        }
    }
}


@Composable
private fun MainInfo(data: MarketDetailsRes, isFavorite: Boolean, onFavorite: () -> Unit) {
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

        IconButton(
            onClick = { onFavorite() }
        ) {
            Icon(
                painter = painterResource(if (isFavorite) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark),
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
private fun BusinessInfo(data: MarketDetailsRes) {
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
        val intent = Intent(Intent.ACTION_VIEW, "kakaomap://search?q=${marketName}".toUri())
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // 카카오맵 앱 미설치 시 웹 버전으로 대체
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                "https://map.kakao.com/?q=${marketName}".toUri()
            )
            context.startActivity(webIntent)
        }
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
