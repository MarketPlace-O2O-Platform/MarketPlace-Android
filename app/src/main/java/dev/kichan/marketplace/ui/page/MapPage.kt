package dev.kichan.marketplace.ui.page

import LargeCategory
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.AuthViewModel
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.CategoryTap
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.EventListItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.IconChip
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.launch

@Composable
fun MapPage(navController: NavController) {

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
                }

                CategoryTap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    selectedCategory = selectedCategory,
                    onSelected = { selectedCategory = it }
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
                    Icon(imageVector = Icons.Outlined.Settings, contentDescription = null, tint = Color(0xff545454))
                }
                
                IconChip(
                    modifier = Modifier.align(Alignment.TopCenter).padding(52.dp),
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
fun SheetContent(modifier: Modifier = Modifier, isExpended: Boolean, onCloseSheet: () -> Unit) {
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
            items(10) {
                EventListItem(
                    modifier = Modifier.padding(12.dp),
                    imageRes = R.drawable.desert,
                    title = "참피온삼겹살 트리플스트리",
                    couponDescription = "맛있는 삼겹살맛있는 삼겹살맛있는 삼겹살맛있는 삼겹살맛있는 삼겹살맛있는 삼겹...",
                    location = "송도",
                    likes = 10,
                    category = LargeCategory.Food.nameKo
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