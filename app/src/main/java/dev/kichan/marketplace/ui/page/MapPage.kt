package dev.kichan.marketplace.ui.page

import LargeCategory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import dev.kichan.marketplace.model.data.coupon.Coupon
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.BottomNavigationBar
import dev.kichan.marketplace.ui.component.CategoryTap
import dev.kichan.marketplace.ui.component.CouponCard
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.IconChip
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun MapPage(navController: NavController) {
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            com.google.android.gms.maps.model.LatLng(
                37.376651978907326,
                126.63425891507083,
            ), 14f
        )
    }

    val expandedHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp * 0.8f
    }

    var selectedCategory by remember { mutableStateOf(LargeCategory.All) }

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
                    onCloseSheet = { scope.launch { bottomSheetState.collapse() } }
                )
            },
            scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState),
            modifier = Modifier.padding(it),
            sheetPeekHeight = 200.dp,
            sheetShape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
            sheetElevation = 100.dp
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
                }

                CategoryTap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    selectedCategory = selectedCategory,
                    onSelected = { selectedCategory = it }
                )
            }
        }
    }
}

@Composable
fun SheetContent(modifier: Modifier = Modifier, isExpended: Boolean, onCloseSheet: () -> Unit) {
    Box(modifier = Modifier) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 20.dp),
            modifier = modifier,
            userScrollEnabled = isExpended
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

        IconChip(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            onClick = { /*TODO*/ },
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
        SheetContent(isExpended = true, onCloseSheet = {})
    }
}


@Preview(showBackground = true)
@Composable
fun MapPagePreview() {
    MarketPlaceTheme {
        MapPage(rememberNavController())
    }
}