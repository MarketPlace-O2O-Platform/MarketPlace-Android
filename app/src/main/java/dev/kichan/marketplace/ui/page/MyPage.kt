package dev.kichan.marketplace.ui.page

import LargeCategory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.AuthViewModel
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.CategorySelector
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.MyPageCard
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun MyPage(navController: NavController, viewModel : AuthViewModel) {
    val member = viewModel.member.observeAsState()
    val myCuration = viewModel.myCuration.observeAsState()

    var selectedCategory by remember {
        mutableStateOf(mutableListOf(LargeCategory.All))
    }

    val onLogout = {
        viewModel.logout(
            onSuccess = {
                navController.popBackStack()
                navController.navigate(Page.Login.name)
            },
            onFail = {
                // 로그아웃 실패 시 처리 로직을 추가
                // 예를 들어, 오류 메시지를 표시할 수 있습니다.
                // 예: showError("로그아웃 실패")
            }
        )
    }

    LaunchedEffect(Unit) {
//        viewModel.getMyCuration()
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(21.dp))

            /* 없어진듯
            // 상단 로그아웃 버튼
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "로그아웃",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        onLogout()
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Outlined.Info, contentDescription = "로그아웃")
            }
            */
            Spacer(modifier = Modifier.height(4.dp))

            // 아이콘과 사용자 이름, 쿠폰함 버튼
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = Color(0xFFDADADA),
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFF9F9F9), shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "${member.value?.studentId}님", fontSize = 16.sp, lineHeight = 22.4.sp, fontWeight = FontWeight.Bold, fontFamily = PretendardFamily)

                    Spacer(modifier = Modifier.width(12.dp))

                    // Dropdown Icon
                    Icon(
                        painter = painterResource(id = R.drawable.down),
                        contentDescription = "드롭다운",
                        tint = Color(0xFF838A94),
                        modifier = Modifier.size(24.dp)
                    )
                }

                // 받은 쿠폰함 버튼
                Button(
                    onClick = {
                        navController.navigate(Page.CouponHam.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    contentPadding = PaddingValues(10.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "받은 쿠폰함")
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // 상단 실선
            Divider(
                color = Color(0xFFF4F4F4),
                thickness = 8.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 나만의 큐레이션과 카테고리 선택 버튼
            CategorySelector(
                selectedCategorys = selectedCategory,
                onChange = { }
            )
            Spacer(modifier = Modifier.height(20.dp))

            // MyPageCard를 세로로 나열하는 리스트, 각 카드 사이에 구분선 추가
            if(myCuration.value.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("저장한 매장이 없습니다.", modifier = Modifier.padding(vertical = 16.dp))
                }
            }
            else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp) // 카드 사이 간격 제거
                ) {
                    items(myCuration.value ?: listOf()) { event ->
                        Column {
                            MyPageCard(event = event)
                            Divider(
                                color = Color(0xFFF4F4F4),
                                thickness = 1.dp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CategorySelectorPreview() {
    MarketPlaceTheme {
        CategorySelector(
            selectedCategorys = listOf(LargeCategory.All),
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPagePreview() {
    MyPage(navController = rememberNavController(), viewModel = AuthViewModel())
}
