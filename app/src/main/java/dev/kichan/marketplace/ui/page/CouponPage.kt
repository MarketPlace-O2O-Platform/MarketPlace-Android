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

@Composable
fun ReceivedCouponsScreen(navController: NavHostController) {
    val viewModel: CouponViewModel = viewModel()
    var selectedTab by remember { mutableStateOf(0) }
    var isDialogShow by remember { mutableStateOf(false) }
    var selectedCouponId by remember { mutableStateOf<Long?>(null) }
    val token = "Bearer YOUR_ACCESS_TOKEN" // ⚠️ 실제 토큰으로 변경 필요

    // API 데이터 관찰
    val coupons by viewModel.coupons.observeAsState(initial = emptyList())
    val couponUsed by viewModel.couponUsed.observeAsState(initial = false)

    // ✅ 쿠폰 목록을 가져오는 로직을 개선
    LaunchedEffect(selectedTab, token) {
        val type = when (selectedTab) {
            0 -> "ISSUED"
            1 -> "USED"
            2 -> "EXPIRED"
            else -> "ISSUED"
        }
        viewModel.fetchCoupons(token, type)
    }

    // ✅ 쿠폰 사용 후 UI 업데이트 (쿠폰이 사용되면 다시 API 호출)
    LaunchedEffect(couponUsed) {
        if (couponUsed) {
            viewModel.fetchCoupons(token, when (selectedTab) {
                0 -> "ISSUED"
                1 -> "USED"
                2 -> "EXPIRED"
                else -> "ISSUED"
            })
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(21.dp))

        // 상단 제목
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 뒤로 가기 아이콘
                Icon(
                    painter = painterResource(R.drawable.left),
                    contentDescription = "Back Icon",
                    tint = Color(0xFF838A94),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(start = 8.dp)
                        .clickable {
                            navController.navigate(Page.My.name)
                        }
                )

                // 타이틀
                Text(
                    text = "받은 쿠폰함",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.weight(1f)
                )
            }
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
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(coupons, key = { it.memberCouponId }) { coupon: CouponResponse ->
                Log.d("CouponPage", "쿠폰 ID: ${coupon.memberCouponId}, 이름: ${coupon.couponName}, 사용 여부: ${coupon.used}")

                CouponCard(
                    onClick = {
                        selectedCouponId = coupon.memberCouponId
                        isDialogShow = true
                    },
                    status = if (coupon.used) "사용 완료" else "사용 가능"
                )
            }
        }

        // ✅ 디버깅용 쿠폰 데이터 UI 추가
        DebugCouponList(coupons)
    }

    // ✅ 쿠폰 사용 다이얼로그 (사용 후 UI 업데이트)
    if (isDialogShow) {
        Dialog(onDismissRequest = { isDialogShow = false }) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "쿠폰을 사용하시겠습니까?", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        onClick = {
                            selectedCouponId?.let {
                                viewModel.useCoupon(token, it)
                            }
                            isDialogShow = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("예")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedButton(
                        onClick = { isDialogShow = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("아니오")
                    }
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