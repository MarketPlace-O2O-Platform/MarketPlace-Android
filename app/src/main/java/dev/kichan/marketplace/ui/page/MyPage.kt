package dev.kichan.marketplace.ui.page

import Carbon_bookmark
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.data.event.Event
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.BottomNavigationBar
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun MyPage(navController: NavController) {
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
            Spacer(modifier = Modifier.height(24.dp))

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
                        // 로그아웃 처리
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Outlined.Info, contentDescription = "로그아웃")
            }

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
                    Text(text = "202000877 님", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                // 받은 쿠폰함 버튼
                Button(
                    onClick = {
                        // navController.navigate(Page.Coupon.name)
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

            // 실선
            Divider(
                color = Color(0xFFF4F4F4),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 나만의 큐레이션과 카테고리 선택 버튼
            CurationCategorySelector()

            // 2x2 그리드 형태로 CurationCard 4개 추가
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (i in 1..10) {
                    item {
                        CurationCard(
                            event = Event(
                                marketName = "콜드케이스 인하대점",
                                eventName = "송도",
                                defaultPrice = 50000,
                                eventPrice = 29500,
                                imageRes = R.drawable.roomex
                            ),
                            imageResId = R.drawable.cafe,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CurationCategorySelector() {
    val scrollState = rememberScrollState()

    Column {
        Text(
            text = "나만의 큐레이션",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val categories = listOf("TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT")

            categories.forEachIndexed { index, category ->
                Button(
                    onClick = { /* TODO: 카테고리 선택 로직 추가 */ },
                    colors = if (index == 0) {
                        // 선택된 버튼 스타일
                        ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
                    } else {
                        // 선택되지 않은 버튼 스타일 (테두리만 보이도록)
                        ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Black)
                    },
                    border = if (index == 0) null else BorderStroke(1.dp, Color(0xFFC6C6C6)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .height(32.dp) // 버튼 높이 감소
                        .widthIn(min = 64.dp) // 버튼 너비 최소값 설정
                ) {
                    Text(text = category, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun CurationCard(modifier: Modifier = Modifier, event: Event, imageResId: Int) {
    var isBookMark by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.aspectRatio(1.0f)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Icon(
                imageVector = if (!isBookMark) Carbon_bookmark else Icons.Filled.Favorite,
                contentDescription = "Bookmark",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clickable { isBookMark = !isBookMark }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(text = event.marketName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "위치",
                    tint = Color.Gray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = event.eventName, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPagePreview() {
    MyPage(navController = rememberNavController())
}
