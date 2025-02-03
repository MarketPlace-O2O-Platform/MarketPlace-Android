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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.coupon.CouponPagination
import dev.kichan.marketplace.model.data.coupon.CouponRes
import dev.kichan.marketplace.model.data.coupon.PopularCouponRes
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.organisms.CategorySelector
import dev.kichan.marketplace.model.repository.CouponRepository
import dev.kichan.marketplace.ui.component.atoms.HomeAppBar
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.molecules.EventList
import dev.kichan.marketplace.ui.data.Event
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomePage(
    navController: NavController,
    singleTonViewModel: SingleTonViewModel = SingleTonViewModel()
) {
    val couponRepo = CouponRepository()
    val latestCoupons = remember { mutableStateOf<CouponPagination<CouponRes>?>(null) }
    val popularCoupons = remember { mutableStateOf<List<PopularCouponRes>?>(null) }

    val getPopularCoupon = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = couponRepo.getPopularCoupon(
                singleTonViewModel.currentMember.value!!.studentId,
                null,
                20
            )
            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    popularCoupons.value = res.body()?.response?.couponResDtos ?: listOf()
                } else {

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        getPopularCoupon()
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
//                item {
//                    Spacer(modifier = Modifier.height(20.dp))
//                    if(popularCoupons.value != null) {
//                        CouponBanner(
//                            bannerList = popularCoupons.value!!.map {
//                                BannerItem(
//                                    title = it.name,
//                                    subTitle = it.deadline,
//                                    description = it.marketName,
//                                    imageUrl = "${NetworkModule.BASE_URL}image/${it.thumbnail}"
//                                )
//                            }
//                        )
//                    }
//                }

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
                        eventList = popularCoupons.value?.map {
                            Event(
                                id = it.id.toString(),
                                title = it.name,
                                subTitle = it.marketName,
                                url = "${NetworkModule.BASE_URL}image/${it.thumbnail}"
                            )
                        } ?: listOf()
                    )
                }
//
//                // 최신 제휴 이벤트
                item {
                    Spacer(modifier = Modifier.height(16.dp))
//                    EventList(
//                        navController = navController,
//                        title = "이번달 신규 이벤트",
//                        eventList = latestCoupons.value?.map {
//                            Event(
//                                id = it.id.toString(),
//                                subTitle = it.marketName,
//                                title = it.name,
//                                url = "${BuildConfig.API_BASE_URL}image/${it.thumbnail}"
//                            )
//                        } ?: listOf()
//                    )
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