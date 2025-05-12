package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.viewmodel.CouponViewModel
import dev.kichan.marketplace.model.data.CouponResponse
import dev.kichan.marketplace.ui.component.atoms.CouponUI
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun ReceivedCouponsScreen(navController: NavHostController, couponViewModel: CouponViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    var isDialogShow by remember { mutableStateOf(false) }
    var selectedCoupon by remember { mutableStateOf<CouponResponse?>(null) }

    val state = couponViewModel.downloadCouponPageState

    LaunchedEffect(Unit) {
        couponViewModel.getDownloadCouponList()
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = state.couponList) { coupon ->
                CouponUI(coupon)
            }
        }
    }
}

//@Composable
//fun DebugCouponList(coupons: List<CouponResponse>) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        coupons.forEach { coupon ->
//            Text(text = "🛒 쿠폰 ID: ${coupon.memberCouponId}, 이름: ${coupon.couponName}, 사용 여부: ${coupon.used}")
//        }
//    }
//}