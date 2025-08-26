package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.common.toLocalDateTime
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.organisms.CategorySelector
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.HomeAppBar
import dev.kichan.marketplace.ui.component.molecules.CouponBoxList
import dev.kichan.marketplace.ui.component.organisms.BannerItem
import dev.kichan.marketplace.ui.component.organisms.CouponBanner
import dev.kichan.marketplace.ui.data.CouponBoxProps
import dev.kichan.marketplace.ui.icon.IcCampaign
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.viewmodel.CouponViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kichan.marketplace.viewmodel.HomeNavigationEvent

@Composable
fun HomePage(
    navController: NavController,
    couponViewModel: CouponViewModel = viewModel()
) {
    val state = couponViewModel.homeState
    LaunchedEffect(Unit) {
        if(state.popularCoupons.isEmpty()) {
            couponViewModel.getClosingCoupon()
            couponViewModel.getLatestCoupon()
            couponViewModel.getPopularCoupon()
        }
        couponViewModel.navigationEvent.collect { event ->
            when (event) {
                HomeNavigationEvent.NavigateToSearch -> navController.navigate(Page.Search.name)
                is HomeNavigationEvent.NavigateToEventDetail -> navController.navigate("${Page.EventDetail.name}/${event.marketId}")
                is HomeNavigationEvent.NavigateToCouponListPage -> navController.navigate("${Page.CouponListPage.name}/${event.type}")
            }
        }
    }

    Scaffold(
        topBar = {
            HomeAppBar(
                logo = R.drawable.logo,
                onSearch = { couponViewModel.onSearchClicked() },
                Icons.Outlined.Notifications to {}
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
                        isLoading = state.isClosingLoading || state.closingCoupon.isEmpty(),
                        bannerList = state.closingCoupon.map {
                            val deadLine = it.deadline.toLocalDateTime()
                            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

                            BannerItem(
                                title = it.name,
                                subTitle = it.marketName,
                                description = "~ " + formatter.format(deadLine),
                                imageUrl = NetworkModule.getImage(it.thumbnail),
                                onClick = { couponViewModel.onEventDetailClicked(it.marketId) }
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
                        couponList = state.popularCoupons.map {
                            CouponBoxProps(
                                id = it.id.toString(),
                                title = it.name,
                                subTitle = it.marketName,
                                url = NetworkModule.getImage(it.thumbnail),
                                marketId = it.marketId,
                                onDownloadClick = { couponViewModel.downloadCoupon(it.id) },
                                isDownload = it.isMemberIssued,
                            )
                        },
                        isLoading =  state.isPopularLoading,
                        onMoreClick = { couponViewModel.onCouponListPageClicked("popular") },
                    )
                }
//                // 최신 제휴 이벤트
                item {
                    val now = LocalDate.now()

                    CouponBoxList(
                        navController = navController,
                        title = "${now.monthValue}월 신규 | 멤버십 혜택",
                        couponList = state.latestCoupons.map {
                            CouponBoxProps(
                                id = it.id.toString(),
                                subTitle = it.marketName,
                                title = it.name,
                                url = NetworkModule.getImage(it.thumbnail),
                                marketId = it.marketId,
                                onDownloadClick = { couponViewModel.downloadCoupon(it.id) },
                                isDownload = it.isMemberIssued,
                            )
                        },
                        isLoading = state.isLatestLoading,
                        onMoreClick = { couponViewModel.onCouponListPageClicked("latest") },
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