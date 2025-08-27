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
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.component.atoms.CouponListItemProps
import dev.kichan.marketplace.viewmodel.CouponViewModel
import java.time.LocalDate

@Composable
fun CouponListPage(
    navController: NavHostController = rememberNavController(),
    couponViewModel: CouponViewModel = viewModel(),
    type: String,
) {
    val now = LocalDate.now()
    val uiState by couponViewModel.couponListUiState.collectAsStateWithLifecycle()
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
            items(uiState.couponList) {
                CouponListItem(
                    modifier = Modifier.clickable { navController.navigate("${Page.EventDetail.name}/${it.marketId}") },
                    props = CouponListItemProps(
                        id = it.couponId,
                        name = it.couponName,
                        marketName = it.marketName,
                        marketId = it.marketId,
                        imageUrl = NetworkModule.getImage(it.thumbnail),
                        address = it.address,
                        isAvailable = it.isAvailable,
                        isMemberIssued = it.isMemberIssued
                    ),
                    onDownloadClick = {id -> couponViewModel.downloadCoupon(id)}
                )
            }
            if(uiState.isLoading) {
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