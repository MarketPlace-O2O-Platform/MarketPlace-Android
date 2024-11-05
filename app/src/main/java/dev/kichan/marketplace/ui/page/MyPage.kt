package dev.kichan.marketplace.ui.page

import Carbon_bookmark
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.focus.focusModifier
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


//MyPage
@Composable
fun MyPage(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) { innerPadding ->  // padding을 추가해 bottomBar와의 충돌 방지
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)  // 여기서 padding 추가
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
                //todo: 로그아웃 버튼 아이콘 수정
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
                    Text(text = "202000877 님", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                }

                // 받은 쿠폰함 버튼
                Button(
                    onClick = { navController.navigate(Page.Coupon.name) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    contentPadding = PaddingValues(8.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "받은 쿠폰함")
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // 실선
            HorizontalDivider(
                color = Color(0xFFF4F4F4),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            //'나만의 큐레이션'
            Text(
                text = "나만의 큐레이션",
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                fontWeight = FontWeight.SemiBold
            )

            // 2x2 그리드 형태로 CurationCard 4개 추가
            Spacer(modifier = Modifier.height(16.dp))

            val curationCardModifier = Modifier.weight(1f)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (i in 1..10) {
                    item {
                        CurationCard(
                            modifier = curationCardModifier,
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

// Updated CurationCard function with event details and image
@Composable
fun CurationCard(modifier: Modifier = Modifier, event: Event, imageResId: Int) {
    var isBookMark by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        // 이미지와 스크랩 아이콘
        Box(
            modifier = Modifier
                .aspectRatio(1.0f)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Icon(
                //todo: 아이콘 변경
                imageVector = if (!isBookMark) Carbon_bookmark else Icons.Filled.Favorite,
                contentDescription = "Bookmark",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clickable { isBookMark = !isBookMark }
            )
        }

        // 가게명과 위치 정보
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = event.marketName,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "위치",
                    tint = Color.Gray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = event.eventName,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPagePreview() {
    MyPage(navController = rememberNavController())
}

@Preview
@Composable
private fun CurationCard() {
    MarketPlaceTheme {
        CurationCard(
            event = Event(
                marketName = "콜드케이스 인하대점",
                eventName = "송도",
                defaultPrice = 50000,
                eventPrice = 29500,
                imageRes = R.drawable.roomex
            ),
            imageResId = R.drawable.cafe
        )
    }
}