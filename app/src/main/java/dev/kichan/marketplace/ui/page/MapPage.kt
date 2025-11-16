package dev.kichan.marketplace.ui.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import dev.kichan.marketplace.ui.component.molecules.MarketListLoadingItem
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

    val scope = rememberCoroutineScope()

    // 현재 시트 단계 추적
    var currentSheetStage by remember { mutableIntStateOf(0) }

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
        }
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
                for (p in uiState.markets) {
                    val isSelected = p.market.marketId == uiState.selectedMarketId

                    MarkerComposable(
                        keys = arrayOf(isSelected, p.market.marketName),
                        state = rememberMarkerState(position = p.coords),
                        anchor = Offset(0.5f, 0.5f),
                        onClick = {
                            mapViewModel.onMarkerClick(p.market.marketId)
                            true
                        }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.wrapContentSize()
                        ) {
                            // 마커 아이콘 (위)
                            Icon(
                                painter = painterResource(
                                    if (isSelected) R.drawable.map_marker
                                    else R.drawable.map_coupon
                                ),
                                contentDescription = p.market.marketName,
                                tint = Color.Unspecified,  // drawable의 원래 색상 사용
                                modifier = Modifier.size(if (isSelected) 48.dp else 14.dp)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // 매장 이름 (아래)
                            Text(
                                text = p.market.marketName,
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_semi_bold)),
                                    color = Color(0xFF121212),
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.widthIn(max = 100.dp)
                            )
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
                    mapViewModel.getMarkets(position)
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
                }
            ) { stage ->
                // 선택된 매장을 최상단으로 정렬
                val displayedMarkets = remember(uiState.selectedMarketId, uiState.markets) {
                    if (uiState.selectedMarketId != null) {
                        uiState.markets.sortedByDescending {
                            it.market.marketId == uiState.selectedMarketId
                        }
                    } else {
                        uiState.markets
                    }
                }

                SheetContent(
                    modifier = Modifier.fillMaxSize(),
                    isExpended = stage == 1,
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
fun SheetContent(
    modifier: Modifier = Modifier,
    onDetailClick: (id: Long) -> Unit,
    isExpended: Boolean,
    isLoading: Boolean,
    markets: List<MarketRes>,
    onFavorite: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        userScrollEnabled = isExpended
    ) {
        if (isLoading) {
            items(10) {
                MarketListLoadingItem()
            }
        }
        if (markets.isNotEmpty()) {
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
            isExpended = true,
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
