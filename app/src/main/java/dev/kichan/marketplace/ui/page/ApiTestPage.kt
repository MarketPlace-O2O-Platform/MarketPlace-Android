package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.CouponViewModel
import dev.kichan.marketplace.model.repository.MarketRepositoryImpl
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.Input
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.molecules.EventList
import dev.kichan.marketplace.ui.data.Event
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ApiTestPage(couponViewModel: CouponViewModel) {
    var search by remember { mutableStateOf("") }
    val coupons = couponViewModel.coupons.observeAsState()

    val marketRepository = MarketRepositoryImpl()

    Column {
        Input(value = search, onChange = { search = it })
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    marketRepository.getMarketSearch(name = search, null, null)
                }
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

