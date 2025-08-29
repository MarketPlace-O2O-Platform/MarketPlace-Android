package dev.kichan.marketplace.ui.page

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.remote.RetrofitClient
import dev.kichan.marketplace.model.dto.MemberRes
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.ProfileHeader
import dev.kichan.marketplace.ui.component.RefundCouponCard
import dev.kichan.marketplace.ui.component.RefundCouponCardSkeleton
import dev.kichan.marketplace.ui.component.atoms.SkeletonItem
import dev.kichan.marketplace.ui.viewmodel.EndedCoupon
import dev.kichan.marketplace.ui.viewmodel.MyPage2ViewModel

@Composable
fun MyPage2(
    navController: NavController,
    myPage2ViewModel: MyPage2ViewModel = viewModel(),
) {
    val uiState by myPage2ViewModel.uiState.collectAsState()

    val tabs = listOf("환급형 쿠폰", "증정형 쿠폰", "끝난 쿠폰")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .background(Color.White)
        ) {
            item { Spacer(modifier = Modifier.height(21.dp)) }
            item {
                if (uiState.member == null) {
                    TopBarUI()
                } else {
                    TopBar(
                        member = uiState.member!!,
                        고객센터_가기 = {},
                        큐레이션_가기 = {}
                    )
                }
            }
            item {
                Divider(
                    color = Color(0xFFF4F4F4),
                    thickness = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth(),
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(2.dp),
                            color = Color.Black
                        )
                    },
                    divider = {} // 하단 디바이더 제거
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            modifier = Modifier.background(Color.White),
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTabIndex == index) Color.Black else Color.Gray
                                )
                            }
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            if (selectedTabIndex == 0) {
                if (uiState.paybackCouponList.isEmpty()) {
                    items(20) {
                        RefundCouponCardSkeleton()
                    }
                } else {
                    items(uiState.paybackCouponList) {
                        RefundCouponCard(
                            storeName = it.marketName,
                            discountTitle = it.couponName,
                            imageUrl = NetworkModule.getImage(it.thumbnail),
                            onClick = { navController.navigate(Page.ReceptUploadPage.name) },
                            modifier = Modifier.padding(horizontal = 18.dp),
                            isUsable = true
                        )
                    }
                }
            }
            if (selectedTabIndex == 1) {
                if (uiState.giftCouponList.isEmpty()) {
                    items(20) {
                        RefundCouponCardSkeleton()
                    }
                } else {
                    items(uiState.giftCouponList) {
                        RefundCouponCard(
                            storeName = it.marketName,
                            discountTitle = it.couponName,
                            imageUrl = it.thumbnail,
                            onClick = { navController.navigate(Page.ReceptUploadPage.name) },
                            modifier = Modifier.padding(horizontal = 18.dp),
                            isUsable = true
                        )
                    }
                }
            }
            if (selectedTabIndex == 2) {
                if (uiState.endedCouponList.isEmpty()) {
                    items(20) {
                        RefundCouponCardSkeleton()
                    }
                } else {
                    items(uiState.endedCouponList) { endedCoupon ->
                        when (endedCoupon) {
                            is EndedCoupon.EndedPayback -> {
                                RefundCouponCard(
                                    storeName = endedCoupon.coupon.marketName,
                                    discountTitle = endedCoupon.coupon.couponName,
                                    imageUrl = NetworkModule.getImage(endedCoupon.coupon.thumbnail),
                                    onClick = { },
                                    modifier = Modifier.padding(horizontal = 18.dp),
                                    isUsable = false
                                )
                            }

                            is EndedCoupon.EndedGift -> {
                                RefundCouponCard(
                                    storeName = endedCoupon.coupon.marketName,
                                    discountTitle = endedCoupon.coupon.couponName,
                                    imageUrl = NetworkModule.getImage(endedCoupon.coupon.thumbnail),
                                    onClick = { },
                                    modifier = Modifier.padding(horizontal = 18.dp),
                                    isUsable = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(
    member: MemberRes,
    큐레이션_가기: () -> Unit,
    고객센터_가기: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 왼쪽: 프로필 영역
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = Color.Gray,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "${member.studentId}님",
                fontSize = 14.sp,
                color = Color.Black
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                tint = Color.Gray
            )
        }

        // 오른쪽: 큐레이션 | 고객센터
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "큐레이션",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.clickable { 큐레이션_가기() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(14.dp)
                        .width(1.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "고객센터",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.clickable { 고객센터_가기() }
                )
            }
        }
    }
}

@Composable
fun TopBarUI() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 왼쪽 프로필 영역
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = Color.Gray,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            SkeletonItem(
                modifier = Modifier
                    .width(30.dp)
                    .height(10.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                tint = Color.Gray
            )
        }

        // 오른쪽 버튼 영역
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "큐레이션",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(14.dp)
                        .width(1.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "고객센터",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}