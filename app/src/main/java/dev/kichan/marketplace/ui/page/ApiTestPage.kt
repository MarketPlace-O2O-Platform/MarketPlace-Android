package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.common.CouponViewModel
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.molecules.EventList
import dev.kichan.marketplace.ui.data.Event
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun ApiTestPage(couponViewModel: CouponViewModel) {
    val coupons = couponViewModel.coupons.observeAsState();

    Column {
        Button(
            onClick = {
                couponViewModel.getCoupons(1L)
            }
        ) { Text("클릭") }

        EventList(
            navController = rememberNavController(),
            title = "쿠폰 목록",
            eventList = coupons.value?.map {
                Event(
                    id = it.id.toString(),
                    title = it.name,
                    subTitle = it.deadLine,
                    url = it.description
                )
            } ?: listOf()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ApiTestPagePreview() {
    MarketPlaceTheme {
        ApiTestPage(CouponViewModel())
    }
}

