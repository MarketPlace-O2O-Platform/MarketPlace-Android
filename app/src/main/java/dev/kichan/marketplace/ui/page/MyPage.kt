package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.login.MemberLoginRes
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.CategorySelector
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.viewmodel.AuthViewModel
import dev.kichan.marketplace.ui.component.molecules.MarketListLoadingItem
import dev.kichan.marketplace.viewmodel.LoginUiState
import dev.kichan.marketplace.viewmodel.MarketViewModel

@Composable
fun MyPage(
    navController: NavController,
    authViewModel: AuthViewModel,
    marketViewModel: MarketViewModel,
) {
    val authState = authViewModel.loginState
    val marketState = marketViewModel.myPageUiState
    var selectedCategory by remember { mutableStateOf(LargeCategory.All) }

    val onLogout = {
        authViewModel.logout(
            onSuccess = {
                // 로그아웃 성공 시 로그인 화면으로 이동
                navController.popBackStack()
                navController.navigate(Page.Login.name)
            },
            onFail = {
                // 실패 시 처리(예: 에러 메시지 출력)
                println("로그아웃 실패: ${it.message}")
            }
        )
    }

    LaunchedEffect(Unit) {
        marketViewModel.getFavorites()
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) { innerPadding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item { Spacer(modifier = Modifier.height(21.dp)) }
            item {
                ProfileHeader(
                    navController = navController,
                    member = (authState as LoginUiState.Success).member,
                    onLogout = onLogout
                )
            }
            item {
                Spacer(modifier = Modifier.height(2.dp))
                Divider(
                    color = Color(0xFFF4F4F4),
                    thickness = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Surface(
                    modifier = Modifier.padding(
                        top = 20.dp,
                        start = PAGE_HORIZONTAL_PADDING,
                        end = PAGE_HORIZONTAL_PADDING,
                        bottom = 8.dp
                    )
                ) {
                    Text(
                        "나만의 큐레이션",
                        fontFamily = PretendardFamily,
                        fontSize = 17.sp,
                        fontWeight = FontWeight(600)
                    )
                }
                CategorySelector(
                    selectedCategory = selectedCategory,
                    onChange = { selectedCategory = it }
                )
            }

            if (marketState.isLoading) {
                items(10) {
                    MarketListLoadingItem()
                }
            } else if (marketState.favorites.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("저장한 매장이 없습니다.", modifier = Modifier.padding(vertical = 16.dp))
                    }
                }
            } else {
                items(items = marketState.favorites) { market ->
                    MarketListItem(
                        title = market.name,
                        description = market.description,
                        location = market.address,
                        imageUrl = NetworkModule.getImage(market.thumbnail),
                        isFavorite = market.isFavorite,
                        onLikeClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    navController: NavController,
    member: MemberLoginRes,
    onLogout: () -> Unit = {},
) {
    var isShowLogoutMemu by remember { mutableStateOf(false) }

    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile",
                tint = Color(0xFFDADADA),
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF9F9F9), shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "${member.studentId}님",
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PretendardFamily
            )

            Spacer(modifier = Modifier.width(8.dp))

            // 드롭다운 아이콘 (터치 가능하도록 IconButton 사용)
            IconButton(onClick = { isShowLogoutMemu = !isShowLogoutMemu }) {
                Icon(
                    painter = painterResource(id = R.drawable.down),
                    contentDescription = "드롭다운",
                    tint = Color(0xFF838A94),
                    modifier = Modifier.size(24.dp)
                )
            }

            DropdownMenu(
                expanded = isShowLogoutMemu,
                onDismissRequest = { isShowLogoutMemu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("로그아웃") },
                    onClick = {
                        onLogout()
                    }
                )
            }
        }
        // 받은 쿠폰함 버튼
        Button(
            onClick = {
                navController.navigate(Page.CouponHam.name)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(10.dp),
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "받은 쿠폰함")
        }
    }
}
//
//
//@Preview(showBackground = true)
//@Composable
//fun MyPagePreview() {
//    MyPage(navController = rememberNavController(), authViewModel = AuthViewModel())
//}
