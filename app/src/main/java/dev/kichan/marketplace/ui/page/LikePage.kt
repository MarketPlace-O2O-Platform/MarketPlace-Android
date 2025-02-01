package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.CategorySelector
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.LikeMarketSearchBar
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun LikePage(navController: NavController) {
    var searchKey by remember { mutableStateOf("") }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item {
                LikeMarketSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    key = searchKey
                ) { searchKey = it }
            }
            item {
                MyHeartCount()
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFEEEEEE))
                )
            }
            item {
                //todo: 컴포넌트 명 변경
                DeadLineOGoitssm()
            }

            item {
                Jigum()
            }
        }
    }
}

@Composable
private fun Jigum() {
    var selectedCategorys by remember {
        mutableStateOf(mutableListOf(LargeCategory.All, LargeCategory.Food))
    }

//    val state = LikeRequest(
//        marketName = "콜드케이스 인하대점",
//        likeCount = 9,
//        imageRes = R.drawable.cafe,
//        isMyDone = false,
//        isRequestDone = false,
//        deadLine = LocalDate.of(2025, 3, 12)
//    )
    SpaceTitle(title = "지금 공감하면 할인권을 드려요", badgeTitle = "EVENT")
    Spacer(modifier = Modifier.height(20.dp))
    CategorySelector(
        selectedCategorys = selectedCategorys,
        onChange = {}
    )
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(horizontal = PAGE_HORIZONTAL_PADDING),
    ) {
        for (i in 1..10) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
//                RequestSmallCard(modifier = Modifier.weight(1.0f), state = state)
//                RequestSmallCard(modifier = Modifier.weight(1.0f), state = state)
            }
        }
    }
}

@Composable
fun SpaceTitle(modifier: Modifier = Modifier, title: String, badgeTitle: String) {
    Row(
        modifier = Modifier.padding(
            horizontal = PAGE_HORIZONTAL_PADDING,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 26.sp,
                fontWeight = FontWeight(600),
                color = Color(0xDE000000),
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            color = Color(0xFF000000),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(
                text = badgeTitle,
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 6.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 16.8.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
private fun DeadLineOGoitssm() {
    Column(
        modifier = Modifier.padding(
            top = 24.dp,
            bottom = 32.dp
        )
    ) {
        SpaceTitle(title = "달성 임박", badgeTitle = "HOT \uD83D\uDD25")
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            val state = LikeRequest(
//                marketName = "콜드케이스 인하대점",
//                likeCount = 100,
//                imageRes = R.drawable.cafe,
//                isMyDone = false,
//                isRequestDone = false,
//                deadLine = LocalDate.of(2025, 3, 12)
//            )

//            items(10) {
//                RequestCard(
//                    modifier = Modifier.width(284.dp),
//                    state = state
//                )
//            }
        }
    }
}

//todo: 더 좋은 이름으로 수정
@Composable
private fun MyHeartCount() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_HORIZONTAL_PADDING, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                // todo: 아이콘 변경
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xff303030)
            )
            Spacer(modifier = Modifier.width(11.dp))
            Text(
                text = "내 공감권 3개",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xDE000000),
                )
            )
            Spacer(modifier = Modifier.width(11.dp))
            Text(text = "1개", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Text(
            text = "공감권은 매일 자정에 충전됩니다.",
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF727272),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LikePagePreview() {
    MarketPlaceTheme {
        LikePage(rememberNavController())
    }
}