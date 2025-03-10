package dev.kichan.marketplace.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.like.TempMarketRes
import dev.kichan.marketplace.model.repository.MarkerLikeRepository
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.CategorySelector
import dev.kichan.marketplace.ui.component.atoms.EmptyMessage
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.LikeMarketSearchBar
import dev.kichan.marketplace.ui.component.molecules.RequestSmallCard
import dev.kichan.marketplace.ui.component.molecules.RequestCard
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LikePage(navController: NavController) {
    val repository = MarkerLikeRepository()
    var searchKey by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(LargeCategory.All) }

    val tempMarkets = remember { mutableStateOf<List<TempMarketRes>>(listOf()) }
    val cheerTempMarkets = remember { mutableStateOf<List<TempMarketRes>>(listOf()) }

    val getCheerTempMarket = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = repository.getCheerMarket(
                202401598,
            )
            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    cheerTempMarkets.value = res.body()!!.response.marketResDtos
                }
            }
        }
    }

    val getTempMarket = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = repository.getTempMarkets(
                202401598,
                20,
            )
            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    tempMarkets.value = res.body()!!.response.marketResDtos
                }
            }
        }
    }

    val getTempMArketSearch = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = repository.getMarketSearch(
                202401598,
                searchKey,
            )
            withContext(Dispatchers.Main) {

            }
        }
    }

    LaunchedEffect(Unit) {
        getTempMarket();
        getCheerTempMarket();
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item {
                LikeMarketSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    key = searchKey
                ) { searchKey = it }
            }
            if (searchKey.isEmpty()) {
                item {
                    MyHeartCount()
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFFEEEEEE))
                    )
                }
                item {
                    SpaceTitle(title = "달성 임박", badgeTitle = "HOT \uD83D\uDD25")
                    Spacer(modifier = Modifier.height(12.dp))
                }
                item {
                    if(cheerTempMarkets.value.isEmpty()) {
                        EmptyMessage()
                    }
                    else {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(items = cheerTempMarkets.value) {
                                RequestCard(
                                    marketName = it.name,
                                    likeCount = it.cheerCount,
                                    thumbnail = NetworkModule.getImage(it.thumbnail, true),
                                    isMyDone = false,
                                    isRequestDone = false
                                )
                            }
                        }
                    }
                }
                item {
                    SpaceTitle(title = "지금 공감하면 할인권을 드려요", badgeTitle = "EVENT")
                    Spacer(modifier = Modifier.height(20.dp))
                    CategorySelector(
                        selectedCategory = selectedCategory,
                        onChange = { selectedCategory = it }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                items(tempMarkets.value.size / 2) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = PAGE_HORIZONTAL_PADDING, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        val market1 = tempMarkets.value[it * 2]
                        RequestCard(
                            modifier = Modifier
                                .weight(1f),
                            marketName = market1.name,
                            likeCount = market1.cheerCount,
                            thumbnail = NetworkModule.getImage(market1.thumbnail, true),
                            isMyDone = market1.isCheer,
                            isRequestDone = false
                        )

                        if (it * 2 + 1 < tempMarkets.value.size) {
                            val market2 = tempMarkets.value[it * 2 + 1]
                            RequestCard(
                                modifier = Modifier
                                    .weight(1f),
                                marketName = market2.name,
                                likeCount = market2.cheerCount,
                                thumbnail = NetworkModule.getImage(market2.thumbnail, true),
                                isMyDone = market2.isCheer,
                                isRequestDone = false
                            )
                        } else {
                            Box(Modifier.weight(1f))
                        }
                    }
                }
            } else {
                item {
                    Text("대충 검색")
                }
            }
        }
    }
}

@Composable
fun SpaceTitle(modifier: Modifier = Modifier, title: String, badgeTitle: String) {
    Row(
        modifier = Modifier.padding(
            horizontal = PAGE_HORIZONTAL_PADDING,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 26.sp,
                fontWeight = FontWeight(600),
                color = Color(0xDE000000),
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            color = Color(0xFF000000),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(
                text = badgeTitle,
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 6.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 16.8.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

//todo: 더 좋은 이름으로 수정
@Composable
private fun MyHeartCount() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_HORIZONTAL_PADDING, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                // todo: 아이콘 변경
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xff303030)
            )
            Spacer(modifier = Modifier.width(11.dp))
            Text(
                text = "내 공감권",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xDE000000),
                )
            )
            Spacer(modifier = Modifier.width(11.dp))
            Text(text = "1개", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Text(
            text = "공감권은 매일 자정에 충전됩니다.",
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF727272),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LikePagePreview() {
    MarketPlaceTheme {
        LikePage(rememberNavController())
    }
}