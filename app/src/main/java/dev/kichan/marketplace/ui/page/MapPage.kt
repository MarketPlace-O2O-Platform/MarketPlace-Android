package dev.kichan.marketplace.ui.page

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.dto.MarketRes
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.HeightBasedBottomSheet
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.CategoryTap
import dev.kichan.marketplace.ui.component.atoms.EmptyMessage
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.IconChip
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.viewmodel.MapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MapPage(
    navController: NavController,
    mapViewModel: MapViewModel = viewModel()
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val uiState by mapViewModel.uiState.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(37.376651978907326, 126.63425891507083), 14f
        )
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // navigationBars 높이 계산
    val navigationBarsHeight = with(density) {
        WindowInsets.navigationBars.getBottom(density).toDp()
    }

    // 앱 하단바 높이 (Material BottomNavigation 기본 높이 + 시스템 네비게이션 바)
    val bottomNavHeight = 56.dp + navigationBarsHeight

    // 현재 시트 단계 추적
    var currentSheetStage by remember { mutableIntStateOf(0) }

    // LazyColumn 스크롤 상태 (Nested Scroll용)
    val listState = rememberLazyListState()

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    val onMoveCurrentPosition = {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { position ->
                CoroutineScope(Dispatchers.Main).launch {
                    position?.let {
                        val userLatLng = LatLng(position.latitude, position.longitude)
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngZoom(userLatLng, 15f),
                            durationMs = 1000
                        )
                    }
                }
            }
    }

    LaunchedEffect(Unit, uiState.selectedCategory) {
        val position = cameraPositionState.position.target
        mapViewModel.getMarkets(position)
    }

    // 시트 높이 정의 (2단계: 접음, 펼침)
    val contentHeight = screenHeight - bottomNavHeight
    val sheetHeights = listOf(190.dp, contentHeight - 100.dp)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                pageList = bottomNavItem
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            GoogleMap(
                onMapLoaded = { },
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = false,
                    mapToolbarEnabled = true
                ),
                onMapClick = { mapViewModel.clearSelection() }
            ) {
                for (group in uiState.markerGroups) {
                    key(group.markets.first().marketId) {
                        val isSelected = uiState.selectedGroupMarketIds?.any { id ->
                            group.markets.any { it.marketId == id }
                        } ?: false

                        Log.d("MapPage", "[마커 렌더링] ${group.markets.first().marketName}, " +
                            "선택=$isSelected, 좌표=${group.coords}, count=${group.count}, " +
                            "key=${group.markets.first().marketId}")

                        MarkerComposable(
                        keys = arrayOf(
                            uiState.selectedCategory,
                            group.markets.first().marketId,  // 대표 매장 ID
                            group.coords.latitude,
                            group.coords.longitude,
                            group.count,
                            isSelected
                        ),
                        state = rememberMarkerState(position = group.coords),
                        anchor = Offset(0.5f, 0.5f),
                        onClick = {
                            mapViewModel.onMarkerGroupClick(group)
                            true
                        }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.wrapContentSize()
                        ) {
                            // 마커 아이콘
                            Icon(
                                painter = painterResource(
                                    if (isSelected) R.drawable.map_marker
                                    else R.drawable.map_coupon
                                ),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(if (isSelected) 48.dp else 14.dp)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // 매장 이름 (항상 표시)
                            OutlinedText(
                                text = if (group.count > 1) {
                                    "${group.markets.first().marketName} 외 ${group.count - 1}곳"
                                } else {
                                    group.markets.first().marketName
                                },
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_semi_bold)),
                                    color = Color(0xFF121212),
                                    textAlign = TextAlign.Center
                                ),
                                outlineColor = Color.White,
                                outlineWidth = 6f,
                                modifier = Modifier.widthIn(max = 150.dp)
                            )
                        }
                    }
                    }
                }
            }

            CategoryTap(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                selectedCategory = uiState.selectedCategory,
                onSelected = {
                    val position = cameraPositionState.position.target
                    mapViewModel.onCategoryChanged(it, position)
                }
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 62.dp, end = 20.dp)
                    .size(36.dp)
                    .background(color = Color(0xffffffff), shape = CircleShape)
                    .border(width = 1.dp, color = Color(0xFFE1E1E1), shape = CircleShape)
                    .clickable { onMoveCurrentPosition() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mage_location),
                    contentDescription = null,
                    tint = Color(0xff545454),
                )
            }

            IconChip(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 62.dp),
                onClick = {
                    val position = cameraPositionState.position.target
                    val bounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
                    mapViewModel.getMarkets(position, bounds)
                },
                icon = Icons.Default.Menu,
                title = "현 지도에서 검색",
                contentColor = Color(0xFF545454),
                backgroundColor = Color(0xFFffffff)
            )

            // "목록 보기" 버튼 - 첫 화면 시트 상단 위로 20dp 고정 (시트 뒤에 가려짐)
            IconChip(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = sheetHeights[0] + 20.dp),
                onClick = { currentSheetStage = 1 },
                icon = Icons.Default.Menu,
                title = "목록 보기",
                contentColor = Color(0xff545454),
                backgroundColor = Color(0xffffffff)
            )

            // 높이 기반 바텀 시트 (앱 하단바 위에서 시작)
            HeightBasedBottomSheet(
                modifier = Modifier.align(Alignment.BottomCenter),
                heights = sheetHeights,
                currentStage = currentSheetStage,
                onStageChange = { newStage ->
                    currentSheetStage = newStage
                },
                listState = listState
            ) { _ ->
                // 그룹 선택 시 해당 매장들만 필터링 + 화면 범위 필터링
                val displayedMarkets = remember(
                    uiState.selectedGroupMarketIds,
                    uiState.markets,
                    uiState.visibleBounds
                ) {
                    // 1. 그룹 선택 필터링
                    val selectedIds = uiState.selectedGroupMarketIds
                    val groupFiltered = if (selectedIds != null) {
                        uiState.markets.filter { it.market.marketId in selectedIds }
                    } else {
                        uiState.markets
                    }

                    // 2. 화면 범위 필터링 (저장된 bounds 사용)
                    val bounds = uiState.visibleBounds
                    if (bounds != null) {
                        groupFiltered.filter { market ->
                            bounds.contains(market.coords)
                        }
                    } else {
                        groupFiltered
                    }
                }

                SheetContent(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    isLoading = uiState.isLoading,
                    markets = displayedMarkets.map { it.market },
                    onDetailClick = { navController.navigate("${Page.EventDetail.name}/$it") },
                    onFavorite = { mapViewModel.favorite(it) }
                )
            }

            // "지도 보기" 버튼 - 펼친 상태일 때만 표시
            if (currentSheetStage == 1) {
                IconChip(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp),
                    onClick = { currentSheetStage = 0 },
                    icon = Icons.Default.LocationOn,
                    title = "지도 보기",
                    contentColor = Color(0xffffffff),
                    backgroundColor = Color(0xff121212)
                )
            }
        }
    }
}


@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    outlineColor: Color = Color.White,
    outlineWidth: Float = 4f
) {
    BoxWithConstraints(modifier = modifier) {
        val textMeasurer = rememberTextMeasurer()
        val density = LocalDensity.current

        val measuredText = remember(text, style, constraints) {
            textMeasurer.measure(
                text = AnnotatedString(text),
                style = style,
                constraints = constraints
            )
        }

        val textSizePx = with(density) { (style.fontSize).toPx() }

        Canvas(
            modifier = Modifier.size(
                width = with(density) { measuredText.size.width.toDp() },
                height = with(density) { measuredText.size.height.toDp() }
            )
        ) {
            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    textSize = textSizePx
                    textAlign = android.graphics.Paint.Align.CENTER
                    isFakeBoldText = true  // 텍스트를 더 진하게
                }

                // 여러 줄 처리
                val lines = text.split("\n")
                val lineHeight = paint.descent() - paint.ascent()
                val totalHeight = lineHeight * lines.size
                var yOffset = -paint.ascent()

                lines.forEach { line ->
                    // Stroke (테두리)
                    paint.style = android.graphics.Paint.Style.STROKE
                    paint.strokeWidth = outlineWidth
                    paint.color = android.graphics.Color.WHITE

                    drawText(
                        line,
                        measuredText.size.width / 2f,
                        yOffset,
                        paint
                    )

                    // Fill (내부)
                    paint.style = android.graphics.Paint.Style.FILL
                    paint.color = android.graphics.Color.BLACK

                    drawText(
                        line,
                        measuredText.size.width / 2f,
                        yOffset,
                        paint
                    )

                    yOffset += lineHeight
                }
            }
        }
    }
}

@Composable
fun SheetContent(
    modifier: Modifier = Modifier,
    state: LazyListState,
    onDetailClick: (id: Long) -> Unit,
    isLoading: Boolean,
    markets: List<MarketRes>,
    onFavorite: (Long) -> Unit
) {
    LazyColumn(
        state = state,
        modifier = modifier.fillMaxWidth()
        // userScrollEnabled 제거 - NestedScroll에서 제어
    ) {
        if (isLoading && markets.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else if (markets.isNotEmpty()) {
            items(markets) {
                val newAddress = it.address.split(" ")
                    .take(2)
                    .joinToString(" ")

                MarketListItem(
                    modifier = Modifier
                        .clickable { onDetailClick(it.marketId) }
                        .padding(12.dp),
                    title = it.marketName,
                    description = it.marketDescription,
                    location = newAddress,
                    imageUrl = NetworkModule.getImage(it.thumbnail),
                    isFavorite = it.isFavorite,
                    onLikeClick = { onFavorite(it.marketId) }
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xffEAEAEA)
                )
            }
        } else {
            item { EmptyMessage(message = "근처에 매장이 없습니다") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SheetContentPreview() {
    MarketPlaceTheme {
        SheetContent(
            state = rememberLazyListState(),
            isLoading = false,
            markets = listOf(),
            onDetailClick = { },
            onFavorite = { }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MapPagePreview() {
    MarketPlaceTheme {
        MapPage(rememberNavController())
    }
}
