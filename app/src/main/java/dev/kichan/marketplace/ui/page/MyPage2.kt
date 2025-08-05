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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.ProfileHeader
import dev.kichan.marketplace.ui.component.RefundCouponCard
import dev.kichan.marketplace.viewmodel.LoginUiState
import dev.kichan.marketplace.viewmodel.LoginViewModel
import dev.kichan.marketplace.viewmodel.MyViewModel


@Composable
fun MyPage2(
    navController: NavController,
    authViewModel: LoginViewModel = LoginViewModel(),
    myViewModel: MyViewModel = MyViewModel(),
) {
    val authState = authViewModel.loginState
    val couponState = myViewModel.state

    val tabs = listOf("환급형 쿠폰", "증정형 쿠폰", "끝난 쿠폰")
    var selectedTabIndex by remember { mutableStateOf(0) }

    val selectedCouponList = if (selectedTabIndex == 0) couponState.paybackCouponList
    else if (selectedTabIndex == 1) couponState.giftCouponList
    else listOf()

    val onLogout = {}

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
                ProfileHeader(
                    navController = navController,
                    member = (authState as LoginUiState.Success).member,
                    onLogout = onLogout
                )
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
            item {
                RefundCouponCard()
            }
//            items(selectedCouponList) {
//                RefundCouponCard()
//            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MyPage2Preview() {
//    MarketPlaceTheme() {
//        MyPage2()
//    }
//}