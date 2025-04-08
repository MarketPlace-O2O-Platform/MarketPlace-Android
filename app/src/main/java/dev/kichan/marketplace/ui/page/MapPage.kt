package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCbrt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.javafaker.Bool
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
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
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.ThreeStepBottomSheet
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.CategoryTap
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.IconChip
import dev.kichan.marketplace.ui.component.molecules.MarketListLoadingItem
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.viewmodel.MarketViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapPage(
    navController: NavController,
    singleTonViewModel: SingleTonViewModel = SingleTonViewModel(),
    marketViewModel: MarketViewModel = MarketViewModel()
) {
    val context = LocalContext.current
    val state = marketViewModel.mapPageState
    val initialPosition = LatLng(
        37.376651978907326,
        126.63425891507083,
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            initialPosition, 14f
        )
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sheetHeights = listOf(100.dp, 220.dp, screenHeight - 50.dp)
    val sheetState = rememberSwipeableState(initialValue = 1)

    val scope = rememberCoroutineScope()
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

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

    LaunchedEffect(Unit) {
        marketViewModel.getMarketByPosition(initialPosition)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                pageList = bottomNavItem
            )
        },
    ) {
        ThreeStepBottomSheet(
            modifier = Modifier.padding(it),
            sheetHeightDp = sheetHeights,
            swipeState = sheetState,
            sheetContent = {
                SheetContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight),
                    isExpended = sheetState.currentValue == 2,
                    isLoading = state.isLoading,
                    markets = state.marketData,
                    onCloseSheet = {
                        scope.launch { sheetState.animateTo(1) };
                    },
                    onDetailClick = { navController.navigate("${Page.EventDetail}/$it") },
                    onFavorite = { marketViewModel.favorite(it) }
                )
            },
            overlayContent = {},
            mainContent = {
                Box {
                    GoogleMap(
                        onMapLoaded = { },
                        cameraPositionState = cameraPositionState,
                        uiSettings = MapUiSettings(
                            zoomControlsEnabled = false,
                            myLocationButtonEnabled = false,
                            mapToolbarEnabled = true
                            //todo: 참고 https://developers.google.com/maps/documentation/android-sdk/reference/com/google/android/libraries/maps/UiSettings?hl=ko
                        ),
                        onMapClick = {
                        }
                    ) {
                        for (p in state.positionList) {
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
                        onClick = { onMoveCurrentPosition() },
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
                        onClick = {
                            val position = cameraPositionState.position.target
                            marketViewModel.getMarketByPosition(position)
                        },
                        icon = Icons.Default.Menu,
                        title = "현 지도에서 검색",
                        contentColor = Color(0xFF545454),
                        backgroundColor = Color(0xFFffffff)
                    )

                    IconChip(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 200.dp),
                        onClick = { scope.launch { sheetState.animateTo(2) } },
                        icon = Icons.Default.Menu,
                        title = "목록 보기",
                        contentColor = Color(0xff545454),
                        backgroundColor = Color(0xffffffff)
                    )
                }
            }
        )
    }
}

@Composable
fun SheetContent(
    modifier: Modifier = Modifier,
    onDetailClick: (id: Long) -> Unit,
    isExpended: Boolean,
    isLoading: Boolean,
    markets: List<MarketRes>,
    onFavorite: (Long) -> Unit,
    onCloseSheet: () -> Unit
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
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
            if(isLoading) {
                items(10) {
                    MarketListLoadingItem()
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
                .padding(bottom = 60.dp),
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
            isLoading = false,
            markets = listOf(),
            onCloseSheet = {},
            onDetailClick = {},
            onFavorite = {}
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