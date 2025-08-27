package dev.kichan.marketplace.ui.page

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.remote.RetrofitClient
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.ProfileHeader
import dev.kichan.marketplace.ui.component.RefundCouponCard
import dev.kichan.marketplace.ui.viewmodel.MyPage2ViewModel

@Composable
fun MyPage2(
    navController: NavController,
    myPage2ViewModel: MyPage2ViewModel = viewModel(),
) {
    val uiState by myPage2ViewModel.uiState.collectAsState()

    val tabs = listOf("환급형 쿠폰", "증정형 쿠폰", "끝난 쿠폰")
    var selectedTabIndex by remember { mutableStateOf(0) }

    val selectedCouponList = if (selectedTabIndex == 0) uiState.paybackCouponList
    else if (selectedTabIndex == 1) uiState.giftCouponList
    else listOf()

    LaunchedEffect(Unit) {
        myPage2ViewModel.getMemberInfo()
        myPage2ViewModel.getPaybackCoupons()
        myPage2ViewModel.getGiftCoupons()
    }

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
                uiState.member?.let { member ->
                    ProfileHeader(
                        member = member,
                        onLogout = { /* TODO: Handle logout */ },
                        onCuration = {},
                        onCallCenter = {}
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
            if(selectedTabIndex == 0) {
                if(uiState.paybackCouponList.isEmpty()){

                }
                else {
                    items(uiState.paybackCouponList) {
                        RefundCouponCard(
                            storeName = "매충 매장 이름",
                            discountTitle = it.couponName,
                            imageUrl = "https://postfiles.pstatic.net/MjAyMzA2MjdfMjgx/MDAxNjg3ODM1MzE3NjQ5.oBDtVqa7bFScuJ308FzHAdmRtABmaL1_SXK17n0-ndQg.KzZ6AcPYVQvHqB_vw4dZp8FG97HJp6bUS4QOU5RatRsg.JPEG.dream_we/IMG_7305.JPG?type=w966",
                            onClick = { navController.navigate(Page.ReceptUploadPage.name) },
                            modifier = Modifier.padding(horizontal = 18.dp),
                        )
                    }
                }
            }
            if(selectedTabIndex == 1) {
                items(uiState.giftCouponList) {
                    RefundCouponCard(
                        storeName = "매충 매장 이름",
                        discountTitle = it.couponName,
                        imageUrl = "https://postfiles.pstatic.net/MjAyMzA2MjdfMjgx/MDAxNjg3ODM1MzE3NjQ5.oBDtVqa7bFScuJ308FzHAdmRtABmaL1_SXK17n0-ndQg.KzZ6AcPYVQvHqB_vw4dZp8FG97HJp6bUS4QOU5RatRsg.JPEG.dream_we/IMG_7305.JPG?type=w966",
                        onClick = { navController.navigate(Page.ReceptUploadPage.name) },
                        modifier = Modifier.padding(horizontal = 18.dp),
                    )
                }
            }
        }
    }
}
