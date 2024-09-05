package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.MoreViewTitle
import dev.kichan.marketplace.model.data.event.Event
import dev.kichan.marketplace.ui.component.DayOfWeekSelector
import dev.kichan.marketplace.ui.component.EventBanner
import dev.kichan.marketplace.ui.component.EventBox
import dev.kichan.marketplace.ui.component.IconAppBar
import dev.kichan.marketplace.ui.component.EventCard
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun HomePage(navController: NavController) {
    Scaffold {
        LazyColumn(
            Modifier
                .padding(it)
                .padding(20.dp)
        )
        {
            item {
                IconAppBar(
                    title = "",
                    Icons.Outlined.Search to {},
                    Icons.Outlined.Notifications to {})
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }

            item { PopularityEvent() }

            item { Spacer(modifier = Modifier.height(50.dp)) }
            item { DeadlineEvent() }

            item { Spacer(modifier = Modifier.height(50.dp)) }
            item { RecentEvent() }
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
                EventCard(event = event)
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