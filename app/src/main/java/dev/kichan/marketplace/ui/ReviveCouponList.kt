package dev.kichan.marketplace.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.model.data.IssuedCouponRes
import dev.kichan.marketplace.ui.component.atoms.CouponItem
import dev.kichan.marketplace.ui.component.atoms.EmptyMessage
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun ReviveCouponList(
    issuedCouponList: List<IssuedCouponRes>,
    usedCouponList: List<IssuedCouponRes>,
    expiredCouponList: List<IssuedCouponRes>,
) {
    var selectedTab by remember { mutableStateOf(0) }

    val selectedTabCouponList = when (selectedTab) {
        0 -> issuedCouponList
        1 -> usedCouponList
        else -> expiredCouponList
    }

    val selectedTabType = when (selectedTab) {
        0 -> "ISSUED"
        1 -> "USED"
        else -> "EXPIRED"
    }

    Column() {
        TabRow(
            modifier = Modifier.border(width = 1.dp, color = Color(0xFFE0E0E0)),
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            listOf("사용 가능", "사용 완료", "기간 만료")
                .forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(text = title, fontSize = 14.sp) }
                    )
                }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 32.dp, horizontal = 20.dp)
        ) {
            if (selectedTabCouponList.isNotEmpty()) {
                items(selectedTabCouponList) { coupon ->
                    CouponItem(coupon, selectedTabType)
                }
            } else {
                item {
                    EmptyMessage(message = "쿠폰이 없습니다.")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviveCouponListPreview() {
    val couponList = listOf<IssuedCouponRes>(
        IssuedCouponRes(
            id = faker.number().randomNumber(),
            couponId = faker.number().randomNumber(),
            thumbnail = faker.company().logo(),
            couponName = faker.company().name(),
            description = faker.lorem().paragraph(),
            deadLine = faker.date().toString(),
            isUsed = faker.bool().bool()
        )
    )

    MarketPlaceTheme {
        ReviveCouponList(
            issuedCouponList = couponList,
            usedCouponList = couponList,
            expiredCouponList = couponList,
        )
    }
}