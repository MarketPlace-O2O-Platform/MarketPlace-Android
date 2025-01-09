package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.data.event.Event
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.LogoAppBar
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.molecules.EventList
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.molecules.SearchBar
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.organisms.CategorySelector
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.organisms.CouponBanner
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.AuthViewModel
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun HomePage(navController: NavController, viewModel: AuthViewModel) {
    val top20 = viewModel.top20Coupon.observeAsState()
    val newEvent = viewModel.newEvent.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getTop20Market()
        viewModel.getNewEvent()
    }

    Scaffold(
        topBar = {
            LogoAppBar(logo = R.drawable.logo, Icons.Outlined.Notifications to {})
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
        ) {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    SearchBar(onSearch = {})
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
                        eventList = top20.value?.map { Event(
                            marketName = it.marketName,
                            eventName = it.name,
                            defaultPrice = 30000,
                            eventPrice = 1000,
                            imageRes = R.drawable.cafe
                        ) } ?: listOf()
                    )
                }

                // 최신 제휴 이벤트
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    EventList(
                        navController = navController,
                        title = "이번달 신규 이벤트",
                        eventList = newEvent.value?.map { Event(
                            marketName = it.marketName,
                            eventName = it.name,
                            defaultPrice = 30000,
                            eventPrice = 1000,
                            imageRes = R.drawable.cafe
                        ) } ?: listOf()
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    MarketPlaceTheme {
        HomePage(rememberNavController(), AuthViewModel())
    }
}