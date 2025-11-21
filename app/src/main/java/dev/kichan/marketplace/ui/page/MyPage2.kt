package dev.kichan.marketplace.ui.page

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.dto.MemberRes
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.RefundCouponCard
import dev.kichan.marketplace.ui.component.RefundCouponCardSkeleton
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.component.atoms.SkeletonItem
import dev.kichan.marketplace.ui.viewmodel.EndedCoupon
import dev.kichan.marketplace.ui.viewmodel.MyPage2ViewModel

@Composable
fun MyPage2(
    navController: NavController,
    myPage2ViewModel: MyPage2ViewModel = viewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by myPage2ViewModel.uiState.collectAsState()

    // 페이지가 보일 때마다 데이터 갱신 (onResume)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                myPage2ViewModel.refreshCoupons()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val tabs = listOf("환급형 쿠폰", "증정형 쿠폰", "끝난 쿠폰")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedCouponId by remember { mutableStateOf<Long?>(null) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        },
        containerColor = Color.White
    ) {
        if (selectedCouponId != null) {
            Dialog({ selectedCouponId = null }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 32.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            "쿠폰을 사용해볼까요?",
                            fontSize = 24.sp,
                            fontWeight = FontWeight(800)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        CustomButton("Yes", modifier = Modifier.fillMaxWidth()) {
                            myPage2ViewModel.useGiftCoupon(selectedCouponId!!)
                            selectedCouponId = null
                            Toast.makeText(context, "쿠폰 사용 완료", Toast.LENGTH_SHORT).show()
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        CustomButton(
                            "No",
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = Color(0xffffffff),
                            border = BorderStroke(1.dp, Color(0xff303030)),
                            textColor = Color(0xff000000),
                        ) { selectedCouponId = null }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(it)
                .background(Color.White)
        ) {
            item { Spacer(modifier = Modifier.height(21.dp)) }
            item {
                if (uiState.member == null) {
                    TopBarUI(
                        고객센터_가기 = {
                            val url = "http://pf.kakao.com/_XkZnn"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        큐레이션_가기 = { navController.navigate(Page.CurationPage.name) },
                        로그아웃_하기 = {}
                    )
                } else {
                    TopBar(
                        member = uiState.member!!,
                        고객센터_가기 = {
                            val url = "http://pf.kakao.com/_XkZnn"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        큐레이션_가기 = { navController.navigate(Page.CurationPage.name) },
                        로그아웃_하기 = {
                            myPage2ViewModel.logout(context) {
                                navController.popBackStack()
                                navController.navigate(Page.Login.name)
                            }
                        }
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
                if (uiState.isLoading) {
                    items(20) {
                        RefundCouponCardSkeleton()
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                } else if (uiState.paybackCouponList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "사용 가능한 쿠폰이 없어요")
                        }
                    }
                } else {
                    items(uiState.paybackCouponList) {
                        val buttonText = if (it.isSubmit && !it.used) {
                            "환급 진행 중"
                        } else {
                            "환급하러 가기"
                        }
                        val isUsable = !(it.isSubmit && !it.used)

                        RefundCouponCard(
                            storeName = it.marketName,
                            discountTitle = it.couponName,
                            imageUrl = NetworkModule.getImage(it.thumbnail),
                            onClick = { navController.navigate(Page.ReceptUploadPage.name + "/${it.memberCouponId}") },
                            modifier = Modifier.padding(horizontal = 18.dp),
                            isUsable = isUsable,
                            buttonText = buttonText
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            if (selectedTabIndex == 1) {
                if (uiState.isLoading) {
                    items(20) {
                        RefundCouponCardSkeleton()
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                } else if (uiState.giftCouponList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "사용 가능한 쿠폰이 없어요")
                        }
                    }
                } else {
                    items(uiState.giftCouponList) {
                        RefundCouponCard(
                            storeName = it.marketName,
                            discountTitle = it.couponName,
                            imageUrl = NetworkModule.getImage(it.thumbnail),
                            onClick = { selectedCouponId = it.memberCouponId },
                            modifier = Modifier.padding(horizontal = 18.dp),
                            isUsable = true,
                            buttonText = "사용하러 가기"
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            if (selectedTabIndex == 2) {
                if (uiState.isLoading) {
                    items(20) {
                        RefundCouponCardSkeleton()
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                } else if (uiState.endedCouponList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "만료된 쿠폰이 없어요")
                        }
                    }
                } else {
                    items(uiState.endedCouponList) { endedCoupon ->
                        when (endedCoupon) {
                            is EndedCoupon.EndedPayback -> {
                                val buttonText = if (endedCoupon.coupon.used) "사용 완료" else "기간 만료"

                                RefundCouponCard(
                                    storeName = endedCoupon.coupon.marketName,
                                    discountTitle = endedCoupon.coupon.couponName,
                                    imageUrl = NetworkModule.getImage(endedCoupon.coupon.thumbnail),
                                    onClick = { },
                                    modifier = Modifier.padding(horizontal = 18.dp),
                                    isUsable = false,
                                    buttonText = buttonText
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                            }

                            is EndedCoupon.EndedGift -> {
                                val buttonText = if (endedCoupon.coupon.used) "사용 완료" else "기간 만료"

                                RefundCouponCard(
                                    storeName = endedCoupon.coupon.marketName,
                                    discountTitle = endedCoupon.coupon.couponName,
                                    imageUrl = NetworkModule.getImage(endedCoupon.coupon.thumbnail),
                                    onClick = { },
                                    modifier = Modifier.padding(horizontal = 18.dp),
                                    isUsable = false,
                                    buttonText = buttonText
                                )
                                Spacer(modifier = Modifier.height(20.dp))
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
    로그아웃_하기: () -> Unit,
) {
    var isShowLogoutMemu by remember { mutableStateOf(false) }
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
            IconButton(
                onClick = { isShowLogoutMemu = !isShowLogoutMemu }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.Gray
                )
            }
        }

        DropdownMenu(
            expanded = isShowLogoutMemu,
            onDismissRequest = { isShowLogoutMemu = false }
        ) {
            DropdownMenuItem(
                text = { Text("로그아웃") },
                onClick = {
                    로그아웃_하기()
                }
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
fun TopBarUI(
    큐레이션_가기: () -> Unit,
    고객센터_가기: () -> Unit,
    로그아웃_하기: () -> Unit,
) {
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