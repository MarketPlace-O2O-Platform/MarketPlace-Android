package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.MoreViewTitle
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.data.event.Event
import dev.kichan.marketplace.ui.component.BottomNavigationBar
import dev.kichan.marketplace.ui.component.DayOfWeekSelector
import dev.kichan.marketplace.ui.component.EventBanner
import dev.kichan.marketplace.ui.component.EventBox
import dev.kichan.marketplace.ui.component.IconAppBar
import dev.kichan.marketplace.ui.component.EventCard
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun HomePage(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        // 아이콘 상단 바
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp) // 좌우 패딩 유지
                .height(42.dp),  // 상단바 아이콘 높이
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* 검색 */ }) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(32.dp)  // 사이즈 조정
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = { /* 알림 */ }) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // 쿠폰 배너 바로 상단바 아래에 위치
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(242.dp)  // 쿠폰 배너 높이
        ) {
            CouponBanner()
        }
        Spacer(modifier = Modifier.height(50.dp))
        // 나머지 요소들은 LazyColumn으로 처리
        LazyColumn(
            Modifier
                .padding(horizontal = 20.dp) // 나머지 요소들에만 패딩 적용
        ) {
            // 카테고리 섹션
            item {
                Text(
                    text = "당신의 일상을 달리해 줄 할인쿠폰",
                    Modifier.padding(vertical = 16.dp),  // "당신의 일상을 달리해 줄 할인쿠폰"과 카테고리 사이 간격 16dp
                    fontSize = 19.sp,  // font-size: 19px
                    fontWeight = FontWeight.SemiBold,  // font-weight: 600 (세미 볼드)
                    lineHeight = 1.sp,  // line-height: 34px
                    textAlign = TextAlign.Left,  // text-align: left
                    //fontFamily = FontFamily(Font(R.font.pretendard))  // Pretendard 폰트 적용
                )
                CategorySelector()
            }


            // 요즘 많이 찾는 제휴 이벤트
            item {
                Spacer(modifier = Modifier.height(50.dp))
                PopularityEvent()
            }

            // 최신 제휴 이벤트
            item {
                Spacer(modifier = Modifier.height(50.dp))
                RecentEvent()
            }
        }
    }
}

@Composable
fun CategorySelector() {
    val categories = listOf("전체보기", "음식", "주점", "카페", "뷰티", "놀이", "기타", "클래스")
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally // Column의 모든 항목 가운데 정렬
    ) {
        categories.chunked(4).forEach { rowItems ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // 각 항목 간 간격을 균등하게 설정
            ) {
                rowItems.forEach { category ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally // 버튼과 텍스트를 가운데 정렬
                    ) {
                        Box(
                            Modifier
                                .size(64.dp)  // 카테고리 가로 세로 크기 64dp로 설정
                                .background(Color.LightGray, shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = category)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp)) // 행 사이 간격 24dp
        }
    }
}

@Composable
fun CouponBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()  // 너비 375px에 해당하는 화면 꽉 차게 설정
            .height(322.dp)  // 높이 242px을 dp로 환산
            .offset()
            .background(Color(0xFF212121))  // 배경 색상 설정
    ) {
        // 텍스트 박스 설정
        Column(
            modifier = Modifier
                .padding( start = 27.dp ,top = 64.dp)
                .size(width = 232.dp, height =157.dp)
        ) {
            Text(text = "[999억 세일즈 페스타]", fontSize = 16.sp, color = Color(0xFF4C74D9))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "SHAREX 강의 즉시할인", fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "선착순 2만원 쿠폰", fontSize = 18.sp, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "지금 다운 받기 >", fontSize = 16.sp, color = Color(0xFFB3B3B3))
        }

        // 쿠폰 박스 설정
        Box(
            modifier = Modifier
                .width(87.dp)
                .height(53.dp)
                .offset(x = 273.dp, y = 108.dp)  // 쿠폰 박스 위치 left 225px, top 81px → 각각 150dp, 54dp로 변환
                .background(Color(0xFF4C74D9), shape = RoundedCornerShape(12.dp)),  // 파란색 배경과 둥근 모서리
            contentAlignment = Alignment.Center
        ) {
            Text(text = "COUPON\n20,000", fontSize = 20.sp, color = Color.White, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun PopularityEvent(modifier: Modifier = Modifier) {
    Column {
        MoreViewTitle("요즘 많이 찾는 제휴 이벤트", {})
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val event = Event(
                marketName = "콜드케이스 인하대점",
                eventName = "방탈출카페 2인권",
                defaultPrice = 50000,
                eventPrice = 29500
            )

            items(5) {
                EventBox(event = event)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeadlineEvent(modifier: Modifier = Modifier) {
    val sampleEvent = Event(
        marketName = "꽃하늘날다 스튜디오",
        eventName = "흑백사진 패키지",
        defaultPrice = 50000,
        eventPrice = 29500
    )
    val _eventList = Array(9) {
        sampleEvent.copy(marketName = sampleEvent.eventName + it.toString())
    }.toList()

    var seletedDayOfWeek by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(
        pageCount = { _eventList.size / 2 + _eventList.size % 2 }
    )

    Column {
        MoreViewTitle("마감 임박 제휴 이벤트", {})
        Spacer(modifier = Modifier.height(20.dp))
        DayOfWeekSelector(seletedDayOfWeek) { seletedDayOfWeek = it }
        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                EventBanner(event = _eventList[2 * page])
                if (_eventList.size > 2 * page + 1) {
                    EventBanner(event = _eventList[2 * page + 1])
                }
            }
        }

        // todo : 페이저 인디케이터 추가
    }
}

@Composable
fun RecentEvent(modifier: Modifier = Modifier) {
    Column {
        MoreViewTitle("최신 제휴 이벤트", {})
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val event = Event(
                marketName = "콜드케이스 인하대점",
                eventName = "2인 디저트 이용권",
                defaultPrice = 50000,
                eventPrice = 29500
            )

            items(5) {
                EventCard(event = event, imageResId = R.drawable.desert)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    MarketPlaceTheme {
        HomePage(rememberNavController())
    }
}