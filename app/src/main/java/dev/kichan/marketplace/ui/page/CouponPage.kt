package dev.kichan.marketplace.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.CouponCard

@Composable
fun ReceivedCouponsScreen() {
    var selectedTab by remember { mutableStateOf(0) } // 선택된 탭의 상태

    Column(modifier = Modifier.fillMaxSize()) {
        // 상단 제목
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(color = Color(0xFFFFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "받은 쿠폰함",
                style = TextStyle(
                    fontSize = 14.sp,
                   // fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )
        }

        // 탭 레이아웃
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            listOf("사용 가능", "사용 완료", "기간 만료").forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                                fontWeight = FontWeight.SemiBold,
                                color = if (selectedTab == index) Color(0xFF000000) else Color(0xFF7D7D7D),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 쿠폰 리스트
        when (selectedTab) {
            0 -> AvailableCouponsList() // 사용 가능한 쿠폰 리스트
            1 -> CompletedCouponsList() // 사용 완료된 쿠폰 리스트
            2 -> ExpiredCouponsList()   // 기간 만료된 쿠폰 리스트
        }
    }
}

@Composable
fun AvailableCouponsList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp), // 패딩 조정
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(5) { // 임의로 5개의 쿠폰을 표시
            CouponCard()
        }
    }
}

@Composable
fun CompletedCouponsList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp), // 패딩 조정
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(3) { // 사용 완료된 쿠폰 예시 (3개)
            CouponCard()
        }
    }
}

@Composable
fun ExpiredCouponsList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp), // 패딩 조정
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(2) { // 기간 만료된 쿠폰 예시 (2개)
            CouponCard()
        }
    }
}
@Composable
fun CouponPage(navController: NavHostController) {
    ReceivedCouponsScreen()
    // '받은 쿠폰함' 화면의 UI 구성
    // 예: 받은 쿠폰 목록을 표시하는 리스트 등
}

@Preview(showBackground = true)
@Composable
fun PreviewReceivedCouponsScreen() {
    ReceivedCouponsScreen()
}
