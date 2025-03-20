package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.model.repository.MarketRepository
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.CategorySelector
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.theme.PretendardFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MyPage(navController: NavController) {
    val repo = MarketRepository()
    var myCuration by remember { mutableStateOf<List<MarketRes>>(listOf()) }
    var selectedCategory by remember { mutableStateOf(LargeCategory.All) }

    val onLogout = {
//        viewModel.logout(
//            onSuccess = {
//                navController.popBackStack()
//                navController.navigate(Page.Login.name)
//            },
//            onFail = {
//                // 로그아웃 실패 시 처리 로직을 추가
//                // 예를 들어, 오류 메시지를 표시할 수 있습니다.
//                // 예: showError("로그아웃 실패")
//            }
//        )
    }

    val onGetMyCulation = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = repo.getFavoriteMarket(
                lastMarketId = null,
                pageSize = 100000
            )
            withContext(Dispatchers.Main) {
                if(res.isSuccessful) {
                    myCuration = res.body()!!.response.marketResDtos
                }
            }
        }
    }

    LaunchedEffect(selectedCategory) {
        onGetMyCulation()
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
                    Text(
                        text = "${202401598}님",
                        fontSize = 16.sp,
                        lineHeight = 22.4.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PretendardFamily
                    )

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
                selectedCategory = selectedCategory,
                onChange = { selectedCategory = it }
            )
            Spacer(modifier = Modifier.height(20.dp))

            // MyPageCard를 세로로 나열하는 리스트, 각 카드 사이에 구분선 추가
            if (myCuration.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("저장한 매장이 없습니다.", modifier = Modifier.padding(vertical = 16.dp))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(0.dp) // 카드 사이 간격 제거
                ) {
                    items(items = myCuration) { market ->
                        MarketListItem(
                            title = market.name,
                            description = market.description,
                            location = market.address,
                            imageUrl = NetworkModule.getImage(market.thumbnail)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPagePreview() {
    MyPage(navController = rememberNavController())
}
