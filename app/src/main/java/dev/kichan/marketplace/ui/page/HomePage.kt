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
import dev.kichan.marketplace.SingleTonViewModel
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

@Composable
fun HomePage(
    navController: NavController,
    singleTonViewModel: SingleTonViewModel = SingleTonViewModel()
) {
    val couponRepo = CouponRepository()
    val latestCoupons = remember { mutableStateOf<List<LatestCouponRes>>(emptyList()) }
    val popularCoupons = remember { mutableStateOf<List<PopularCouponRes>>(listOf()) }
    val closingCoupons = remember { mutableStateOf<List<ClosingCouponRes>>(listOf()) }

    val getPopularCoupon = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = couponRepo.getPopularCoupon(
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

    val getLatestCoupon = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = couponRepo.getLatestCoupon(
                null,
                null,
                20,
            )
            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    latestCoupons.value = res.body()?.response?.couponResDtos ?: listOf()
                } else {

                }
            }
        }
    }

    val getClosingCoupon = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = couponRepo.getClosingCoupon(10)
            withContext(Dispatchers.Main) {
                if(res.isSuccessful) {
                    closingCoupons.value = res.body()?.response ?: listOf()
                }
                else {

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        getPopularCoupon();
        getLatestCoupon();
        getClosingCoupon();
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
                        bannerList = closingCoupons.value.map {
                            val deadLine = it.deadline.toLocalDateTime()
                            Log.d("deadlien", deadLine.toString())
                            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

                            BannerItem(
                                title = it.name,
                                subTitle = it.marketName,
                                description = "~ " + formatter.format(deadLine),
                                imageUrl = NetworkModule.getImage(it.thumbnail)
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
                    Spacer(modifier = Modifier.height(16.dp))
                    EventList(
                        navController = navController,
                        title = "Top 20 인기 페이지",
                        couponList = popularCoupons.value.map {
                            Event(
                                id = it.id.toString(),
                                title = it.name,
                                subTitle = it.marketName,
                                url = NetworkModule.getImage(it.thumbnail)
                            )
                        },
                        onMoreClick = { navController.navigate("${Page.CouponListPage}/popular") },
                    )
                }
//                // 최신 제휴 이벤트
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    EventList(
                        navController = navController,
                        title = "이번달 신규 이벤트",
                        couponList = latestCoupons.value.map {
                            Event(
                                id = it.id.toString(),
                                subTitle = it.marketName,
                                title = it.name,
                                url = NetworkModule.getImage(it.thumbnail)
                            )
                        },
                        onMoreClick = { navController.navigate("${Page.CouponListPage}/latest") },
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