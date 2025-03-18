package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.model.repository.MarketRepository
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MarketListPage(
    nacController: NavHostController = rememberNavController(),
    _category: LargeCategory
) {
    val marketRepository = MarketRepository()

    var marketData by remember { mutableStateOf<List<MarketRes>>(listOf()) }

    val onLoadData : (LargeCategory) -> Unit = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = marketRepository.getMarkets(
                lastMarketId = null,
                category = if(it == LargeCategory.All) null else it.backendLable,
                pageSize = 20
            )

            withContext(Dispatchers.Main) {
                if(res.isSuccessful){
                    val body = res.body()!!.response.marketResDtos
                    marketData += body
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        onLoadData(_category)
    }

    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(_category.toString())
            Text(marketData.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarketListPagePreview() {
    MarketPlaceTheme() {
        MarketListPage(
            _category = LargeCategory.All
        )
    }
}