package dev.kichan.marketplace.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.CouponCard
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun ReceivedCouponsScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) } // 선택된 탭의 상태
    var isDialogShow by remember { mutableStateOf(false) } // 다이얼로그 상태

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(21.dp))

// 상단 제목
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(color = Color(0xFFFFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon to navigate back
                Icon(
                    painter = painterResource(R.drawable.left), // Replace with your actual back icon resource
                    contentDescription = "Back Icon",
                    tint = Color(0xFF838A94),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(start = 8.dp)
                        .clickable {
                            Log.d("tag","clicked")

                            navController.navigate(Page.My.name)
                            // navController.popBackStack()
                            // Navigate to the previous screen (MyPage)
                            // Example: navController.popBackStack()
                        }
                )

                // Title Text
                Text(
                    text = "받은 쿠폰함",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.weight(1f) // Center the title
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
                    text = {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 30.sp,
                                fontFamily = PretendardFamily,
                                fontWeight = FontWeight(400),
                                color = if (selectedTab == index) Color(0xFF303030) else Color(
                                    0xFF868686
                                ),
                                letterSpacing = 0.28.sp,
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
            0 -> AvailableCouponsList(onCouponClick = {
                Log.d("click", "클릭됨")
                isDialogShow = true
            }) // 사용 가능한 쿠폰 리스트
            1 -> CompletedCouponsList(onCouponClick = { isDialogShow = true })
            2 -> ExpiredCouponsList(onCouponClick = { isDialogShow = true })
        }
    }

    if (isDialogShow) {
        // 다이얼로그 팝업이 활성화되면 뒤 배경을 반투명 회색으로 설정
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)) // 반투명 회색 배경 추가
        ) {
            Dialog(onDismissRequest = { isDialogShow = false }, properties = DialogProperties()) {
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp), // 내부 여백
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 타이틀 텍스트
                    Text(
                        text = "쿠폰을 사용하시겠습니까?",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(bottom = 24.dp) // 아래 여백
                    )

                    // 확인 버튼
                    Button(
                        onClick = { isDialogShow = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp), // 버튼 간 간격
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "예",
                            color = Color.White,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                    }

                    // 취소 버튼
                    OutlinedButton(
                        onClick = { isDialogShow = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "아니오",
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AvailableCouponsList(onCouponClick: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(5) {
            CouponCard(onClick= onCouponClick, status= "사용 가능")
        }
    }
}

@Composable
fun CompletedCouponsList(onCouponClick: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(3) {
            CouponCard(onClick= {}, status= "사용 완료")
        }
    }
}

@Composable
fun ExpiredCouponsList(onCouponClick: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(2) {
            CouponCard(onClick= {}, status= "기간 만료")
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
