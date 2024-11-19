package dev.kichan.marketplace.ui.page

import LargeCategory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.MoreViewTitle
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.data.event.Event
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.BottomNavigationBar
import dev.kichan.marketplace.ui.component.DayOfWeekSelector
import dev.kichan.marketplace.ui.component.EventBanner
import dev.kichan.marketplace.ui.component.EventBox
import dev.kichan.marketplace.ui.component.EventCard
import dev.kichan.marketplace.ui.component.IconAppBar
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.PagerCounter
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun HomePage(navController: NavController) {
    Scaffold(
        topBar = {
            IconAppBar(title = "쿠러미", Icons.Outlined.Notifications to {})
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
//                .fillMaxSize()
        ) {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    SearchBar()
                }
                // 쿠폰 배너 바로 상단바 아래에 위치
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    CouponBanner()
                }

                // 카테고리 섹션
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    CategorySelector(navController)
                    Spacer(modifier = Modifier.height(32.dp))

                    HorizontalDivider(
                        thickness = 8.dp,
                        color = Color(0xffEEEEEE)
                    )
                }

                // Top 20 인기 페이지"
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    EventList(
                        navController = navController,
                        title = "Top 20 인기 페이지",
                        eventList = List(5) {
                            Event(
                                marketName = "콜드케이스 인하대점",
                                eventName = "방탈출카페 2인권",
                                defaultPrice = 50000,
                                eventPrice = 29500,
                                imageRes = R.drawable.cafe
                            )
                        }
                    )
                }

                // 최신 제휴 이벤트
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    EventList(
                        navController = navController,
                        title = "이번달 신규 이벤트",
                        eventList = List(5) {
                            Event(
                                marketName = "콜드케이스 인하대점",
                                eventName = "방탈출카페 2인권",
                                defaultPrice = 50000,
                                eventPrice = 29500,
                                imageRes = R.drawable.roomex
                            )
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun CouponBanner(modifier: Modifier = Modifier) {
    val bannerList = listOf(
        R.drawable.banner_2,
        R.drawable.banner_2,
        R.drawable.banner_2,
        R.drawable.banner_2,
        R.drawable.banner_2,
        R.drawable.banner_2,
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { bannerList.size })

    Box(
        Modifier
            .padding(horizontal = PAGE_HORIZONTAL_PADDING)
            .clip(shape = RoundedCornerShape(12.dp))
    ) {
        HorizontalPager(state = pagerState) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(335.0f / 360) // 배너 이미지 비율
            ) {
                Image(
                    painter = painterResource(id = bannerList[it]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        PagerCounter(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            pagerState = pagerState
        )
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .padding(horizontal = PAGE_HORIZONTAL_PADDING)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color(0xff121212),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Spacer(
                modifier = Modifier
                    .background(Color(0xffff0000))
                    .width(1.dp)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.width(10.dp))

            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Normal),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            ) { innerTextField ->
                if (searchText.text.isEmpty()) {
                    Text(
                        text = "찾으시는 이용권을 검색해 보세요.",
                        style = TextStyle(color = Color(0xffB0B0B0)),
                    )
                }
                innerTextField()
            }
        }
    }
}

@Composable
fun CategorySelector(navController: NavController) {
    val categories = LargeCategory.entries

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_HORIZONTAL_PADDING),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        categories.chunked(4).forEach { rowItems ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // 각 항목 간 간격을 균등하게 설정
            ) {
                rowItems.forEach { category ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally, // 버튼과 텍스트를 가운데 정렬
                        modifier = Modifier.clickable {
                            navController.navigate("${Page.PopularEvent.name}/${category.name}")
                        }
                    ) {
                        Image(painter = painterResource(id = category.icon), contentDescription = null)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = category.nameKo)
                    }
                }
            }
        }
    }
}

@Composable
fun EventList(
    modifier: Modifier = Modifier,
    navController: NavController,
    title: String,
    eventList: List<Event>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        MoreViewTitle(
            modifier = Modifier.padding(horizontal = PAGE_HORIZONTAL_PADDING),
            title = title
        ) {
            navController.navigate("${Page.PopularEvent.name}/${LargeCategory.All.name}")
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING)
        ) {
            items(eventList) {
                EventBox(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Page.EventDetail.name)
                        }
                        .fillParentMaxSize(0.8f)
                        .aspectRatio(1f / 1),
                    event = it
                )
            }
        }
    }
}

//todo: 미래에 내가 파일별로 쪼개서 관리할 예정

@Composable
fun DeadlineEvent(modifier: Modifier = Modifier) {
    val sampleEvent = Event(
        marketName = "꽃하늘날다 스튜디오",
        eventName = "흑백사진 패키지",
        defaultPrice = 50000,
        eventPrice = 29500,
        imageRes = R.drawable.roomex
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
            "이번달 신규 이벤트",
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
                eventPrice = 29500,
                imageRes = R.drawable.roomex
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