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
import dev.kichan.marketplace.CouponViewModel
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.organisms.CategorySelector
import dev.kichan.marketplace.ui.component.organisms.CouponBanner
import dev.kichan.marketplace.AuthViewModel
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.ui.component.atoms.HomeAppBar
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.molecules.EventList
import dev.kichan.marketplace.ui.component.organisms.BannerItem
import dev.kichan.marketplace.ui.data.Event
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun HomePage(
    navController: NavController,
    authViewModel: AuthViewModel,
    couponViewModel: CouponViewModel
) {
    val latestCoupons = couponViewModel.latestCoupon.observeAsState()
    val closingCoupons = couponViewModel.closingCoupon.observeAsState()

    LaunchedEffect(Unit) {
        couponViewModel.getClosingCoupon()
        couponViewModel.getLatestCoupon()
    }

    Scaffold(
        topBar = {
            HomeAppBar(logo = R.drawable.logo, {}, Icons.Outlined.Notifications to {})
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
                    if(closingCoupons.value.isNullOrEmpty()) {
                        val images = listOf(
                            "https://github.com/kichan05/kichan05/blob/main/assets/banner_2.png?raw=true",
                            "https://github.com/kichan05/kichan05/blob/main/assets/banner_3.png?raw=true",
                        )
                        CouponBanner(
                            bannerList = images.map({
                                BannerItem(
                                    title = "",
                                    subTitle = "",
                                    description = "",
                                    imageUrl = it
                                )
                            })
                        )
                    }
                    else {
                        CouponBanner(
                            bannerList = closingCoupons!!.value!!.map({
                                BannerItem(
                                    title = it.marketName,
                                    subTitle = it.name,
                                    description = it.deadline,
                                    imageUrl = "${BuildConfig.API_BASE_URL}image/${it.thumbnail}"
                                )
                            })
                        )
                    }
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
//                item {
//                    Spacer(modifier = Modifier.height(16.dp))
//                    EventList(
//                        navController = navController,
//                        title = "Top 20 인기 페이지",
//                        eventList = top20.value?.map { Event(
//                            id = it.id.toString(),
//                            title = it.name,
//                            subTitle = it.marketName,
//                            url = it.thumbnail
//                        ) } ?: listOf()
//                    )
//                }
//
//                // 최신 제휴 이벤트
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    EventList(
                        navController = navController,
                        title = "이번달 신규 이벤트",
                        eventList = latestCoupons.value?.map {
                            Event(
                                id = it.id.toString(),
                                subTitle = it.marketName,
                                title = it.name,
                                url = "${BuildConfig.API_BASE_URL}image/${it.thumbnail}"
                            )
                        } ?: listOf()
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
        HomePage(rememberNavController(), AuthViewModel(), CouponViewModel())
    }
}