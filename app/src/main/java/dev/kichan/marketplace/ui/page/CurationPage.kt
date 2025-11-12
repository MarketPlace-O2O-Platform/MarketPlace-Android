package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.component.molecules.MarketListLoadingItem
import dev.kichan.marketplace.ui.viewmodel.CurationPageViewModel

@Composable
fun CurationPage(
    nacController: NavHostController,
) {
    val context = LocalContext.current
    val curationPageViewModel: CurationPageViewModel = viewModel()
    val listState = rememberLazyListState()
    val uiState by curationPageViewModel.uiState.collectAsState()

    val isScrolledToEnd by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(isScrolledToEnd) {
        if (isScrolledToEnd) {
            curationPageViewModel.getFavoriteMarkets(false)
        }
    }

    // 페이지가 화면에 나타날 때마다 찜한 매장 목록 새로고침
    DisposableEffect(nacController.currentBackStackEntry) {
        val entry = nacController.currentBackStackEntry
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                curationPageViewModel.refreshFavorites()
            }
        }
        entry?.lifecycle?.addObserver(observer)

        onDispose {
            entry?.lifecycle?.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            NavAppBar("찜한 매장") { nacController.popBackStack() }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            LazyColumn(
                state = listState
            ) {
                items(uiState.markets) {market ->
                    MarketListItem(
                        modifier = Modifier.clickable { nacController.navigate("${Page.EventDetail.name}/${market.marketId}") },
                        title = market.marketName,
                        description = market.marketDescription,
                        location = market.address,
                        imageUrl = NetworkModule.getImage(market.thumbnail),
                        isFavorite = market.isFavorite,
                        onLikeClick = { curationPageViewModel.unfavorite(market.marketId) }
                    )
                }

                if(uiState.isLoading) {
                    items(count = 15) {
                        MarketListLoadingItem()
                    }
                }
            }
        }
    }
}
