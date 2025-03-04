package dev.kichan.marketplace.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.ui.component.atoms.Button

class RequestMapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RequestMapScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestMapScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        /** 뒤로가기 버튼 & 타이틀 **/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(end = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.left_black),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "요청하기",
                fontSize = 14.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Divider(color = Color(0xFFEEEEEE), thickness = 1.dp, modifier = Modifier.fillMaxWidth())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            /** 제목 텍스트 **/
            Text(
                text = "인천대 떡볶이 인천대점",
                fontSize = 24.sp,
                lineHeight = 36.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(800),
                color = Color(0xFF333333),
            )

            Spacer(modifier = Modifier.height(8.dp))

            /** 설명 텍스트 **/
            Text(
                text = "인천 연수구 아카데미로 119 건물 2층",
                fontSize = 16.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF5E5E5E),
            )

            Spacer(modifier = Modifier.height(20.dp))

            /** Google Map 추가 **/
            GoogleMapView()
        }
    }
}

@Composable
fun GoogleMapView() {
    val incheonUniversity = LatLng(37.375, 126.632) // 인천대학교 위치
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(incheonUniversity, 15f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp) // 지도 높이 설정
            .background(color = Color(0xFFE0E0E0)) // 박스 배경색 추가

    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = true
            )
        )
    }
    Spacer(modifier = Modifier.height(85.dp))

    Button(
        text = "입점 요청하기",
        onClick = { println("매장 요청하기 버튼 클릭됨!") },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        textColor = Color.White,
        backgroundColor = Color(0xFF303030), // 🔹 명확한 배경색 지정
        shape = RoundedCornerShape(4.dp), // 🔹 버튼 모서리 둥글게
        isDisable = false
    )

}

@Preview(showBackground = true)
@Composable
fun PreviewRequestMapScreen() {
    RequestMapScreen()
}
