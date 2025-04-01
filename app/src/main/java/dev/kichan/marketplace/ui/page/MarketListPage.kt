package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.model.repository.MarketRepository
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.CategoryTap
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.viewmodel.MarketViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class Pagenation<T>(
    val lastId: T?,
    val hasNext: Boolean
)

@Composable
fun MarketListPage(
    nacController: NavHostController = rememberNavController(),
    marketViewModel: MarketViewModel = MarketViewModel(),
    _category: LargeCategory
) {
    val listState = rememberLazyListState()
    val state = marketViewModel.marketPageUiState
    var category by remember { mutableStateOf(_category) }

    LaunchedEffect(category) {
        marketViewModel.getMarketData(category, true, null)
    }

    Scaffold(
        topBar = {
            NavAppBar("카테고리") { nacController.popBackStack() }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            CategoryTap(selectedCategory = category) { category = it }

            LazyColumn(
                state = listState
            ) {
                items(state.marketData) {market ->
                    MarketListItem(
                        modifier = Modifier.clickable { nacController.navigate("${Page.EventDetail.name}/${market.id}") },
                        title = market.name,
                        description = market.description,
                        location = market.address,
                        imageUrl = NetworkModule.getImage(market.thumbnail),
                        isFavorite = market.isFavorite,
                        onLikeClick = { marketViewModel.favorite(market.id) }
                    )
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
            _category = LargeCategory.All
        )
    }
}

//@Composable
//fun MarketListItem() {
//
//}
//
//@Preview(showBackground = true)
//@Composable
//fun MarketListItemPreview() {
//    MarketPlaceTheme() {
//        MarketListItem()
//    }
//}
