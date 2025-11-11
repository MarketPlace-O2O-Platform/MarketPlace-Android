package dev.kichan.marketplace.ui.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
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

    // 시트 높이 정의 (3단계: 접음, 중간, 펼침)
    val contentHeight = screenHeight - bottomNavHeight
    val sheetHeights = listOf(220.dp, 400.dp, contentHeight - 100.dp)

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
                onMapClick = { }
            ) {
                for (p in uiState.markets) {
                    Marker(
                        state = MarkerState(position = p.coords)
                    )
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

            IconButton(
                onClick = { onMoveCurrentPosition() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 52.dp, end = 12.dp)
                    .background(color = Color(0xffffffff), shape = CircleShape)
                    .border(width = 1.dp, color = Color(0xFFE1E1E1), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xff545454)
                )
            }

            IconChip(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(52.dp),
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
                onClick = { currentSheetStage = 2 },
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
                SheetContent(
                    modifier = Modifier.fillMaxSize(),
                    isExpended = stage == 2,
                    isLoading = uiState.isLoading,
                    markets = uiState.markets.map { it.market },
                    onDetailClick = { navController.navigate("${Page.EventDetail.name}/$it") },
                    onFavorite = { mapViewModel.favorite(it) }
                )
            }

            // "지도 보기" 버튼 - 펼친 상태일 때만 표시
            if (currentSheetStage == 2) {
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
