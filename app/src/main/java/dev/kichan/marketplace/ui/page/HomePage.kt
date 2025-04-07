package dev.kichan.marketplace.ui.page

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.common.toLocalDateTime
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.coupon.ClosingCouponRes
import dev.kichan.marketplace.model.data.coupon.LatestCouponRes
import dev.kichan.marketplace.model.data.coupon.PopularCouponRes
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.organisms.CategorySelector
import dev.kichan.marketplace.model.repository.CouponRepository
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.HomeAppBar
import dev.kichan.marketplace.ui.component.molecules.EventList
import dev.kichan.marketplace.ui.component.organisms.BannerItem
import dev.kichan.marketplace.ui.component.organisms.CouponBanner
import dev.kichan.marketplace.ui.data.Event
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.viewmodel.CouponViewModel
import dev.kichan.marketplace.viewmodel.HomeUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

@Composable
fun HomePage(
    navController: NavController,
    couponViewModel: CouponViewModel = CouponViewModel()
) {
    val state = couponViewModel.homeState
    LaunchedEffect(Unit) {
        couponViewModel.getClosingCoupon()
        couponViewModel.getLatestCoupon()
        couponViewModel.getPopularCoupon()
    }

    Scaffold(
        topBar = {
            HomeAppBar(
                logo = R.drawable.logo,
                onSearch = { navController.navigate(Page.Search.name) },
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
                        isLoading = state.isClosingLoading,
                        bannerList = state.closingCoupon.map {
                            val deadLine = it.deadline.toLocalDateTime()
                            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

                            BannerItem(
                                title = it.name,
                                subTitle = it.marketName,
                                description = "~ " + formatter.format(deadLine),
                                imageUrl = NetworkModule.getImage(it.thumbnail),
                                onClick = { navController.navigate("${Page.EventDetail.name}/${it.marketId}") }
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
                    EventList(
                        navController = navController,
                        title = "Top 20 인기 페이지",
                        couponList = state.popularCoupons.map {
                            Event(
                                id = it.id.toString(),
                                title = it.name,
                                subTitle = it.marketName,
                                url = NetworkModule.getImage(it.thumbnail),
                                marketId = it.marketId,
                            )
                        },
                        isLoading =  state.isPopularLoading,
                        onMoreClick = { navController.navigate("${Page.CouponListPage.name}/popular") },
                    )
                }
//                // 최신 제휴 이벤트
                item {
                    EventList(
                        navController = navController,
                        title = "이번달 신규 이벤트",
                        couponList = state.latestCoupons.map {
                            Event(
                                id = it.id.toString(),
                                subTitle = it.marketName,
                                title = it.name,
                                url = NetworkModule.getImage(it.thumbnail),
                                marketId = it.marketId,
                            )
                        },
                        isLoading = state.isLatestLoading,
                        onMoreClick = { navController.navigate("${Page.CouponListPage.name}/latest") },
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