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
import dev.kichan.marketplace.ui.component.atoms.CouponItem
import dev.kichan.marketplace.ui.component.atoms.EmptyMessage
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun ReceivedCouponsScreen(navController: NavHostController, couponViewModel: CouponViewModel) {
    val state = couponViewModel.downloadCouponPageState

    LaunchedEffect(Unit) {
        couponViewModel.getDownloadCouponList("ISSUED")
        couponViewModel.getDownloadCouponList("EXPIRED")
        couponViewModel.getDownloadCouponList("USED")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(21.dp))

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
    }
}