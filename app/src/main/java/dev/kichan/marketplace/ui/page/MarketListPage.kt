package dev.kichan.marketplace.ui.page

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.CategoryTap
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.viewmodel.MarketListViewModel
import dev.kichan.marketplace.ui.viewmodel.MarketListViewModelFactory

@Composable
fun MarketListPage(
    nacController: NavHostController = rememberNavController(),
    category: LargeCategory
) {
    val context = LocalContext.current
    val marketListViewModel: MarketListViewModel = viewModel(
        factory = MarketListViewModelFactory(context.applicationContext as Application, category)
    )
    val listState = rememberLazyListState()
    val uiState by marketListViewModel.uiState.collectAsState()

    val isScrolledToEnd by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(isScrolledToEnd, uiState.isLoading, uiState.hasNext) {
        if (isScrolledToEnd && !uiState.isLoading && uiState.hasNext) {
            marketListViewModel.getMarkets(false)
        }
    }


    // 페이지가 화면에 나타날 때마다 북마크 상태 새로고침
    DisposableEffect(nacController.currentBackStackEntry) {
        val entry = nacController.currentBackStackEntry
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                marketListViewModel.refreshBookmarkStates()
            }
        }
        entry?.lifecycle?.addObserver(observer)

        onDispose {
            entry?.lifecycle?.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            NavAppBar("카테고리") { nacController.popBackStack() }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            CategoryTap(selectedCategory = uiState.selectedCategory) {
                marketListViewModel.onCategoryChanged(it)
            }

            LazyColumn(
                state = listState
            ) {
                if (uiState.isLoading && uiState.markets.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                } else if (uiState.markets.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("매장이 없습니다")
                        }
                    }
                } else {
                    items(uiState.markets) {market ->
                        MarketListItem(
                            modifier = Modifier.clickable { nacController.navigate("${Page.EventDetail.name}/${market.marketId}") },
                            title = market.marketName,
                            description = market.marketDescription,
                            location = market.address,
                            imageUrl = NetworkModule.getImage(market.thumbnail),
                            isFavorite = market.isFavorite,
                            onLikeClick = { marketListViewModel.favorite(market.marketId) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarketListPagePreview() {
    MarketPlaceTheme {
        MarketListPage(
            category = LargeCategory.All
        )
    }
}
