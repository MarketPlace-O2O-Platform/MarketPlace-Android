package dev.kichan.marketplace.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dev.kichan.marketplace.SingleTonViewModel
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.model.service.MarketService
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.CategoryTap
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.IconChip
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.viewmodel.MarketViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MapPage(
    navController: NavController,
    singleTonViewModel: SingleTonViewModel = SingleTonViewModel(),
    marketViewModel: MarketViewModel = MarketViewModel()
) {
    //제발되게 해주세요ㅕ 제발요 2222
    val marketService = NetworkModule.getService(MarketService::class.java)
    val kakaoService = NetworkModule.getKakaoService()

    val marketList = remember { mutableStateOf<List<MarketRes>>(listOf()) }
    val marketPositionList = remember { mutableStateOf<List<LatLng>>(listOf()) }

    val getMarkets = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = marketService.getMarkets(
                null,
                null,
                null,
            )

            val marketData = res.body()!!.response.marketResDtos

            val positionList = marketData.map { kakaoService.getAddress(query = it.address) }
                .filter { it.isSuccessful }
                .map { it.body()!!.documents }
                .filter { it.isNotEmpty() }
                .map {
                    Log.d("Position", it.toString())
                    it[0]
                }
                .map { LatLng(it.y.toDouble(), it.x.toDouble()) }

            Log.d("PositionList", positionList.toString())

            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    marketList.value = marketData
                    marketPositionList.value = positionList
                }
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                37.376651978907326,
                126.63425891507083,
            ), 14f
        )
    }
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scope = rememberCoroutineScope()

    val expandedHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp * 0.8f
    }

    LaunchedEffect(Unit) {
        getMarkets()
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                pageList = bottomNavItem
            )
        },
    ) {
        BottomSheetScaffold(
            sheetContent = {
                SheetContent(
                    modifier = Modifier.height(expandedHeight),
                    isExpended = bottomSheetState.isExpanded,
                    markets = marketList.value,
                    onCloseSheet = { scope.launch { bottomSheetState.collapse() } },
                    onDetailClick = { navController.navigate("${Page.EventDetail}/$it") },
                    onFavorite = { marketViewModel.favorite(it) }
                )
            },
            scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState),
            modifier = Modifier.padding(it),
            sheetPeekHeight = 200.dp,
            sheetShape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
            sheetElevation = 3.dp
        ) { innerPadding ->
            Box {
                GoogleMap(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    onMapLoaded = { },
                    cameraPositionState = cameraPositionState,
                    uiSettings = MapUiSettings(
                        zoomControlsEnabled = false,
                        myLocationButtonEnabled = true,
                    ),
                    onMapClick = {
                    }
                ) {
                    for (p in marketPositionList.value) {
                        Marker(
                            state = MarkerState(position = p)
                        )
                    }
                }

                CategoryTap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    selectedCategory = LargeCategory.All,
                    onSelected = { }
                )

                IconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 52.dp, end = 12.dp)
                        .background(color = Color(0xffffffff), shape = CircleShape)
                        .border(width = 1.dp, color = Color(0xFFE1E1E1), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                        tint = Color(0xff545454)
                    )
                }

                IconChip(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(52.dp),
                    onClick = { /*TODO*/ },
                    icon = Icons.Default.Menu,
                    title = "현 지도에서 검색",
                    contentColor = Color(0xFF545454),
                    backgroundColor = Color(0xFFffffff)
                )

                IconChip(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 220.dp),
                    onClick = { scope.launch { bottomSheetState.expand() } },
                    icon = Icons.Default.Menu,
                    title = "목록 보기",
                    contentColor = Color(0xff545454),
                    backgroundColor = Color(0xffffffff)
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
    markets: List<MarketRes>,
    onFavorite: (Long) -> Unit,
    onCloseSheet: () -> Unit
) {
    Box(modifier = Modifier) {
        LazyColumn(
            modifier = modifier,
            userScrollEnabled = isExpended
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
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
            items(markets) {
                MarketListItem(
                    modifier = Modifier
                        .clickable { onDetailClick(it.id) }
                        .padding(12.dp),
                    title = it.name,
                    description = it.description,
                    location = it.address,
                    imageUrl = NetworkModule.getImage(it.thumbnail),
                    isFavorite = it.isFavorite,
                    onLikeClick = { onFavorite(it.id) }
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xffEAEAEA)
                )
            }
        }

        IconChip(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            onClick = { onCloseSheet() },
            icon = Icons.Default.LocationOn,
            title = "지도 닫기",
            contentColor = Color(0xffffffff),
            backgroundColor = Color(0xff121212)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SheetContentPreview() {
    MarketPlaceTheme {
        SheetContent(
            isExpended = true,
            markets = listOf(),
            onCloseSheet = {},
            onDetailClick = {},
            onFavorite = {})
    }
}


@Preview(showBackground = true)
@Composable
fun MapPagePreview() {
    MarketPlaceTheme {
        MapPage(rememberNavController())
    }
}