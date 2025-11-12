package dev.kichan.marketplace.ui.page

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.HomeAppBar
import dev.kichan.marketplace.ui.component.molecules.CouponBoxList
import dev.kichan.marketplace.ui.component.organisms.BannerItem
import dev.kichan.marketplace.ui.component.organisms.CategorySelector
import dev.kichan.marketplace.ui.component.organisms.CouponBanner
import dev.kichan.marketplace.ui.data.CouponBoxProps
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.viewmodel.HomeNavigationEvent
import dev.kichan.marketplace.ui.viewmodel.HomeViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomePage(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getClosingCoupons()
        homeViewModel.getLatestCoupons()
        homeViewModel.getPopularCoupons()

        homeViewModel.navigationEvent.collect { event ->
            when (event) {
                is HomeNavigationEvent.NavigateToSearch -> navController.navigate(Page.Search.name)
                is HomeNavigationEvent.NavigateToEventDetail -> navController.navigate("${Page.EventDetail.name}/${event.marketId}")
                is HomeNavigationEvent.NavigateToCouponListPage -> navController.navigate("${Page.CouponListPage.name}/${event.type}")
            }
        }
    }

    Scaffold(
        topBar = {
            HomeAppBar(
                logo = R.drawable.logo,
                onSearch = { homeViewModel.onSearchClicked() },
                Icons.Outlined.Notifications to {
                    navController.navigate(Page.AlertPage.name)
                }
            )
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
                // 쿠폰 배너 바로 상단바 아래에 위치
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    CouponBanner(
                        isLoading = uiState.isClosingLoading || uiState.closingCoupons.isEmpty(),
                        bannerList = uiState.closingCoupons.mapNotNull { coupon ->
                            val deadlineText = if (coupon.deadline != null) {
                                try {
                                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                                    val deadLine = LocalDateTime.parse(coupon.deadline, formatter)
                                    "~ " + DateTimeFormatter.ofPattern("yyyy.MM.dd").format(deadLine)
                                } catch (e: Exception) {
                                    if (BuildConfig.DEBUG) {
                                        Log.e("HomePage", "마감임박 쿠폰 날짜 형식 오류: ${coupon.couponId}", e)
                                    }
                                    return@mapNotNull null
                                }
                            } else {
                                "상시 발급 가능"
                            }

                            BannerItem(
                                title = coupon.couponName,
                                subTitle = coupon.marketName,
                                description = deadlineText,
                                imageUrl = NetworkModule.getImage(coupon.thumbnail),
                                onClick = { homeViewModel.onEventDetailClicked(coupon.marketId) }
                            )
                        }
                    )
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
                    CouponBoxList(
                        navController = navController,
                        title = "Top 20 인기 | 멤버십 혜택",
                        couponList = uiState.popularCoupons.map {
                            CouponBoxProps(
                                id = it.couponId.toString(),
                                title = it.couponName,
                                subTitle = it.marketName,
                                url = NetworkModule.getImage(it.thumbnail),
                                marketId = it.marketId,
                                onDownloadClick = { /* TODO */ },
                            )
                        },
                        isLoading = uiState.isPopularLoading,
                        onMoreClick = { homeViewModel.onCouponListPageClicked("popular") },
                    )
                }
//                // 최신 제휴 이벤트
                item {
                    val now = LocalDate.now()

                    CouponBoxList(
                        navController = navController,
                        title = "${now.monthValue}월 신규 | 멤버십 혜택",
                        couponList = uiState.latestCoupons.map {
                            CouponBoxProps(
                                id = it.couponId.toString(),
                                subTitle = it.marketName,
                                title = it.couponName,
                                url = NetworkModule.getImage(it.thumbnail),
                                marketId = it.marketId,
                                onDownloadClick = { /* TODO */ },
                            )
                        },
                        isLoading = uiState.isLatestLoading,
                        onMoreClick = { homeViewModel.onCouponListPageClicked("latest") },
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
        HomePage(rememberNavController())
    }
}
