package dev.kichan.marketplace.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.component.atoms.CouponListItem
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.component.molecules.MarketListLoadingItem
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.viewmodel.CouponViewModel
import java.time.LocalDate

@Composable
fun CouponListPage(
    navController: NavHostController = rememberNavController(),
    couponViewModel: CouponViewModel = CouponViewModel(),
    type: String,
) {
    val now = LocalDate.now()
    val state = couponViewModel.couponListPageState
    val title = if(type == "popular") "인기 쿠폰" else "${now.monthValue}월 신규"

    LaunchedEffect(Unit) {
        couponViewModel.getCouponList(type)
    }

    Scaffold(
        topBar = {
            NavAppBar("$title | 멤버십 혜택") { navController.popBackStack() }
        }
    ) {
        LazyColumn (
            modifier = Modifier.padding(it)
        ) {
            items(state.couponList) {
                CouponListItem(
                    modifier = Modifier.clickable { navController.navigate("${Page.EventDetail.name}/${it.marketId}") },
                    props = it,
                    onDownloadClick = {id -> couponViewModel.downloadCoupon(id)}
                )
            }
            if(state.isLoading) {
                items(15) {
                    MarketListLoadingItem()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponListPagePreview() {
    MarketPlaceTheme() {
        CouponListPage(type = "popular")
    }
}