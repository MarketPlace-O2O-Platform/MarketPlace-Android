package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.javafaker.Bool
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.model.repository.MarketRepository
import dev.kichan.marketplace.ui.component.atoms.CategorySelector
import dev.kichan.marketplace.ui.component.atoms.CategoryTap
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
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
    _category: LargeCategory
) {
    val marketRepository = MarketRepository()

    val listState = rememberLazyListState()
    var marketData by remember { mutableStateOf<List<MarketRes>>(listOf()) }
    var category by remember { mutableStateOf(_category) }
    var pagenation by remember { mutableStateOf(Pagenation<Long>(null, true)) }

    val onLoadData: (LargeCategory, Boolean) -> Unit = {it, isInit ->
        CoroutineScope(Dispatchers.IO).launch {
            val res = marketRepository.getMarkets(
                lastMarketId = if(isInit) null else pagenation.lastId.toString(),
                category = if (it == LargeCategory.All) null else it.backendLable,
                pageSize = 2002
            )

            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    val body = res.body()!!.response.marketResDtos
                    marketData = body
                    listState.scrollToItem(0)
                }
            }
        }
    }

    LaunchedEffect(category) {
        onLoadData(category, true)
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
                items(marketData) {market ->
                    MarketListItem(
                        title = market.name,
                        couponDescription = market.description,
                        location = market.address,
                        imageUrl = NetworkModule.getImage(market.thumbnail)
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
