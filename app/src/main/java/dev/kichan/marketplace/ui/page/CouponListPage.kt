package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.component.atoms.CouponListItem
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.component.molecules.MarketListLoadingItem
import dev.kichan.marketplace.ui.component.LoginDialog
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.CouponListItemProps
import dev.kichan.marketplace.viewmodel.AuthViewModel
import dev.kichan.marketplace.viewmodel.CouponViewModel
import java.time.LocalDate

@Composable
fun CouponListPage(
    navController: NavHostController = rememberNavController(),
    couponViewModel: CouponViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    type: String,
) {
    val now = LocalDate.now()
    val uiState by couponViewModel.couponListUiState.collectAsStateWithLifecycle()
    val authState by authViewModel.uiState.collectAsState()
    var showLoginDialog by remember { mutableStateOf(false) }
    val title = if(type == "popular") "인기 쿠폰" else "${now.monthValue}월 신규"
    val listState = rememberLazyListState()

    val isScrolledToEnd by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(isScrolledToEnd) {
        if (isScrolledToEnd) {
            couponViewModel.loadMoreCoupons(type)
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.checkLoginStatus(navController.context)
        couponViewModel.getCouponList(type)
    }

    if (showLoginDialog) {
        LoginDialog(onDismiss = { showLoginDialog = false }) {
            navController.navigate(Page.Login.name)
        }
    }

    Scaffold(
        topBar = {
            NavAppBar("$title | 멤버십 혜택") { navController.popBackStack() }
        }
    ) {
        LazyColumn (
            modifier = Modifier.padding(it),
            state = listState
        ) {
            items(uiState.couponList) { coupon ->
                CouponListItem(
                    modifier = Modifier.clickable { navController.navigate("${Page.EventDetail.name}/${coupon.marketId}") },
                    props = coupon,
                    onDownloadClick = {id -> 
                        if(authState.isLoggedIn) couponViewModel.downloadCoupon(id)
                        else showLoginDialog = true
                    }
                )
            }
            if(uiState.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
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