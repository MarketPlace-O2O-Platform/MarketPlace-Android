package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
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
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.DayOfWeekSelector
import dev.kichan.marketplace.ui.component.EventBanner
import dev.kichan.marketplace.ui.component.EventBox
import dev.kichan.marketplace.ui.component.EventCard
import dev.kichan.marketplace.ui.component.PagerIndicator
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun HomePage(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        // 아이콘 상단 바
        Row(
            Modifier
                .fillMaxWidth(),
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

        LazyColumn {
            item {
                // 쿠폰 배너 바로 상단바 아래에 위치
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CouponBanner()
                }
            }

            // 카테고리 섹션
            item {
                Spacer(modifier = Modifier.height(50.dp))
                CategorySelector()
            }

            // 요즘 많이 찾는 제휴 이벤트
            item {
                MoreViewTitle(
                    Modifier.padding(horizontal = PAGE_HORIZONTAL_PADDING),
                    "요즘 많이 찾는 제휴 이벤트",
                    { navController.navigate(Page.PopularEvent.name) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16 .dp))
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
fun CouponBanner(modifier: Modifier = Modifier) {
    val bannerList = listOf(
        R.drawable.banner,
        R.drawable.banner,
        R.drawable.banner,
        R.drawable.banner,
        R.drawable.banner,
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { bannerList.size })

    Box {
        HorizontalPager(state = pagerState) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(9 / 5.8f) // 배너 이미지 비율
            ) {
                Image(
                    painter = painterResource(id = bannerList[it]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        PagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            pagerState = pagerState,
        )
    }
}

@Composable
fun CategorySelector() {
    val categories = listOf("전체보기", "음식", "주점", "카페", "뷰티", "놀이", "기타", "클래스")

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_HORIZONTAL_PADDING),
    ) {
        Text(
            text = "당신의 일상을 달리해 줄 할인쿠폰",
            fontSize = 19.sp,  // font-size: 19px
            fontWeight = FontWeight.SemiBold,  // font-weight: 600 (세미 볼드)
            lineHeight = 1.sp,  // line-height: 34px
            textAlign = TextAlign.Left,  // text-align: left
            fontFamily = PretendardFamily  // Pretendard 폰트 적용
        )

        Spacer(modifier = Modifier.height(16.dp))

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
fun PopularityEvent(modifier: Modifier = Modifier) {
    Column {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING)
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
        MoreViewTitle(
            Modifier.padding(horizontal = PAGE_HORIZONTAL_PADDING),
            "마감 임박 제휴 이벤트",
            {}
        )
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
        MoreViewTitle(
            Modifier.padding(horizontal = PAGE_HORIZONTAL_PADDING),
            "최신 제휴 이벤트",
            {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING)
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