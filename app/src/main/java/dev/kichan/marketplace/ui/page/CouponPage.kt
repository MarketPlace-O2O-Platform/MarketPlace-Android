package dev.kichan.marketplace.ui.page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.model.data.coupon.Coupon
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.BottomNavigationBar
import dev.kichan.marketplace.ui.component.CouponCard
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CouponPage(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = listOf(
                Page.Home to Icons.Filled.Home,
                Page.Like to Icons.Filled.Favorite,
                Page.Map to Icons.Filled.LocationOn,
                Page.My to Icons.Filled.Person
            ))
        }
    ) { innerPadding -> // paddingValues를 innerPadding으로 전달
        LazyColumn(
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 20.dp,
                //bottom = innerPadding.calculateBottomPadding() // 바텀 네비게이션 바의 높이를 고려
            ),
            modifier = modifier
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {  // 뒤로 가기 버튼
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                    Spacer(modifier = Modifier.width(80.dp))

                    Text(
                        text = "받은 쿠폰함",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(2.dp)) }

            // 실선
            item {
                HorizontalDivider(
                    color = Color(0xFFEAEAEA),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item { Spacer(modifier = Modifier.height(21.dp)) }

            items(10) {
                CouponCard(
                    coupon = Coupon(
                        id = 0,
                        marketId = 0,
                        name = "커트 2,000원 할인 $it",
                        description = null,
                        deadline = LocalDate.of(2024, 10, 31),
                        count = 0,
                        isHidden = false,
                        isDeleted = false,
                        createdAt = LocalDate.now(),
                        modifiedAt = null
                    ),
                    imageUrl = "https://via.placeholder.com/150" // 임시
                )

                // 쿠폰 아이템 사이에 있는 구분선
                if (it != 9) { // 마지막이 아니면 보임
                    Spacer(modifier = Modifier.height(20.dp))
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(Color(0xfff4f4f4))
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CouponPagePreview() {
    MarketPlaceTheme {
        CouponPage(navController = rememberNavController())  // navController 전달
    }
}
