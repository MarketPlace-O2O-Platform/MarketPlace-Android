package dev.kichan.marketplace.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.CouponCard
import dev.kichan.marketplace.viewmodel.CouponViewModel
import dev.kichan.marketplace.model.data.CouponResponse
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun ReceivedCouponsScreen(navController: NavHostController) {
    val viewModel: CouponViewModel = viewModel()
    var selectedTab by remember { mutableStateOf(0) }
    var isDialogShow by remember { mutableStateOf(false) }
    var selectedCoupon by remember { mutableStateOf<CouponResponse?>(null) }
    val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDIyMDE0NjkiLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTc0MjM4ODA5MCwiZXhwIjoxNzQ0OTgwMDkwfQ.anjETPfYxY_qQFhj6abyk4GYurt67hnEwve5YhvyhpU"

    // API 데이터 관찰
    val coupons by viewModel.coupons.observeAsState(initial = emptyList())
    val couponUsed by viewModel.couponUsed.observeAsState(initial = false)

    // ✅ 쿠폰 목록 가져오기 + 로깅 → 한 번만 호출
    LaunchedEffect(selectedTab, token) {
        val type = when (selectedTab) {
            0 -> "ISSUED"
            1 -> "USED"
            2 -> "EXPIRED"
            else -> "ISSUED"
        }
        Log.d("CouponPage", "쿠폰 리스트 요청: type=$type")

        // 실제 API 호출
        viewModel.fetchCoupons(token, type)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(21.dp))

        // ✅ 중앙 정렬된 타이틀
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "받은 쿠폰함",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                    fontFamily = PretendardFamily
                ),
                textAlign = TextAlign.Center
            )

            // 뒤로 가기 아이콘 (왼쪽 상단 배치)
            Icon(
                painter = painterResource(R.drawable.left),
                contentDescription = "Back Icon",
                tint = Color(0xFF838A94),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterStart)
                    .padding(start = 12.dp)
                    .clickable { navController.navigate(Page.My.name) }
            )
        }

        // 탭 레이아웃
        TabRow(
            modifier = Modifier.border(width = 1.dp, color = Color(0xFFE0E0E0)),
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            listOf("사용 가능", "사용 완료", "기간 만료").forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(text = title, fontSize = 14.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ 쿠폰 리스트 (로그 출력 추가)
        val filteredCoupons = when (selectedTab) {
            0 -> coupons.filter { !it.used }  // 사용 가능한 쿠폰
            1 -> coupons.filter { it.used }   // 사용 완료 쿠폰
            2 -> coupons                      // 기간 만료 쿠폰
            else -> coupons
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredCoupons, key = { it.memberCouponId }) { coupon ->
                Log.d("CouponPage", "쿠폰 표시: ID=${coupon.memberCouponId}, 사용 여부=${coupon.used}")

                CouponCard(
                    coupon = coupon, // ✅ CouponResponse 객체 전달
                    onClick = {
                        selectedCoupon = coupon
                        isDialogShow = true
                    }
                )
            }
        }

        // ✅ 디버깅용 쿠폰 데이터 UI 추가
        DebugCouponList(coupons)
    }

    // ✅ 쿠폰 사용 다이얼로그 (UI 개선)
    if (isDialogShow && selectedCoupon != null) {
        Dialog(onDismissRequest = { isDialogShow = false }) {
            Column(
                modifier = Modifier
                    .width(320.dp) // 다이얼로그 가로 크기
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp) // 모서리 둥글게
                    )
                    .padding(horizontal = 24.dp, vertical = 20.dp), // 안쪽 여백
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 다이얼로그 제목
                Text(
                    text = "${selectedCoupon?.couponName} 쿠폰을 사용하시겠습니까?",
                    fontSize = 20.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center,
                    fontFamily = PretendardFamily
                )

                Spacer(modifier = Modifier.height(20.dp))

                // [사용하기] 검은색 버튼
                Button(
                    onClick = {
                        selectedCoupon?.let {
                            viewModel.useCoupon(token, it.memberCouponId)
                        }
                        isDialogShow = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "사용하기",
                        fontSize = 12.sp,
                        lineHeight = 16.8.sp,
                        color = Color.White,
                        fontWeight = FontWeight(500),
                        textAlign = TextAlign.Center,
                        fontFamily = PretendardFamily
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // [취소] 흰색 버튼(검은색 테두리)
                OutlinedButton(
                    onClick = { isDialogShow = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White, // 흰 배경
                        contentColor = Color.Black    // 텍스트/아이콘 검은색
                    ),
                    border = ButtonDefaults.outlinedButtonBorder, // 기본 테두리 = 1dp
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "취소",
                        fontSize = 12.sp,
                        lineHeight = 16.8.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(500),
                        fontFamily = PretendardFamily
                    )
                }
            }
        }
    }
}

@Composable
fun DebugCouponList(coupons: List<CouponResponse>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "📌 쿠폰 데이터 리스트", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        coupons.forEach { coupon ->
            Text(text = "🛒 쿠폰 ID: ${coupon.memberCouponId}, 이름: ${coupon.couponName}, 사용 여부: ${coupon.used}")
        }
    }
}

@Composable
fun CouponPage(navController: NavHostController) {
    ReceivedCouponsScreen(navController)
}

@Preview(showBackground = true)
@Composable
fun PreviewReceivedCouponsScreen() {
    ReceivedCouponsScreen(rememberNavController())
}