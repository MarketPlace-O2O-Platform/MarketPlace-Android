package dev.kichan.marketplace.ui.page

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.data.remote.RetrofitClient
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.CategoryTap
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.component.molecules.MarketListLoadingItem
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

    LaunchedEffect(isScrolledToEnd) {
        if (isScrolledToEnd) {
            marketListViewModel.getMarkets()
        }
    }

    Scaffold(
        topBar = {
            NavAppBar("카테고리") { nacController.popBackStack() }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            CategoryTap(selectedCategory = uiState.selectedCategory) {
                marketListViewModel.onCategoryChanged(it)
            }

            LazyColumn(
                state = listState
            ) {
                items(uiState.markets) {market ->
                    MarketListItem(
                        modifier = Modifier.clickable { nacController.navigate("${Page.EventDetail.name}/${market.marketId}") },
                        title = market.marketName,
                        description = market.marketDescription,
                        location = market.address,
                        imageUrl = RetrofitClient.getClient().baseUrl().toString() + "images/" + market.thumbnail,
                        isFavorite = market.isFavorite,
                        onLikeClick = { marketListViewModel.favorite(market.marketId) }
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

@Preview(showBackground = true)
@Composable
fun MarketListPagePreview() {
    MarketPlaceTheme {
        MarketListPage(
            category = LargeCategory.All
        )
    }
}
