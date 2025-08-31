package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dev.kichan.marketplace.model.dto.kakao.local.Place
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.ui.viewmodel.RequestViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RequestPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RequestViewModel = viewModel()
) {
    val searchQuery = viewModel.searchQuery
    val searchResults = viewModel.searchResults
    val selectedPlace = viewModel.selectedPlace
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(37.5665, 126.9780), 10f) // Default to Seoul
    }

    LaunchedEffect(selectedPlace.value) {
        selectedPlace.value?.let { place ->
            val latLng = LatLng(place.y.toDouble(), place.x.toDouble())
            cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.requestResult.collectLatest { success ->
            val message = if (success) "요청에 성공했습니다." else "요청에 실패했습니다."
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            NavAppBar("요청하기") { navController.popBackStack() }
        }
    ) {
        LazyColumn(
            modifier = modifier
                .padding(it)
                .padding(
                    top = 52.dp,
                    start = 20.dp,
                    end = 20.dp
                )
                .fillMaxSize()
        ) {
            item {
                StoreSearchUi(
                    query = searchQuery.value,
                    onQueryChange = {
                        searchQuery.value = it
                        viewModel.searchPlaces()
                    }
                )
            }

            if (selectedPlace.value != null) {
                item {
                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        cameraPositionState = cameraPositionState
                    ) {
                        selectedPlace.value?.let { place ->
                            val latLng = LatLng(place.y.toDouble(), place.x.toDouble())
                            Marker(
                                state = MarkerState(position = latLng),
                                title = place.place_name,
                                snippet = place.address_name
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
                item {
                    CustomButton("입점 요청하기", Modifier.fillMaxWidth()) {
                        viewModel.createRequestMarket()
                    }
                }
            } else {
                items(searchResults.value) { place ->
                    PlaceListItem(place, Modifier) {
                        viewModel.onPlaceSelected(place)
                    }
                }
            }
        }
    }
}

@Composable
fun StoreSearchUi(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "쿠폰 받고 싶은 매장을\n검색해주세요",
            fontFamily = PretendardFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight(800)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text("매장명 또는 지번, 도로명으로 검색")
            },
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,      // 포커스 시에도 회색
                unfocusedBorderColor = Color.Gray,    // 비포커스 시 회색
                focusedLabelColor = Color.Gray,       // 포커스 라벨 색
                unfocusedLabelColor = Color.Gray,     // 비포커스 라벨 색
                cursorColor = Color.Black             // 커서 색만 필요시 지정
            )
        )
    }
}

@Composable
fun PlaceListItem(
    place: Place,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        // 매장명
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = place.place_name,
                fontFamily = PretendardFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight(500),
                color = Color(0xff333333)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 도로명 주소가 있으면 도로명, 없으면 지번 주소
            Text(
                text = if (place.road_address_name.isNotEmpty()) place.road_address_name else place.address_name,
                fontFamily = PretendardFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight(500),
                color = Color(0xff7D7D7D)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}
