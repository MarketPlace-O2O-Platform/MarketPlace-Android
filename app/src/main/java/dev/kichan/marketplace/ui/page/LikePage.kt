package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.remote.RetrofitClient
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.CategorySelector
import dev.kichan.marketplace.ui.component.atoms.EmptyMessage
import dev.kichan.marketplace.ui.component.atoms.LikeMarketSearchBar
import dev.kichan.marketplace.ui.component.molecules.RequestCard
import dev.kichan.marketplace.ui.viewmodel.LikeViewModel

@Composable
fun LikePage(
    navController: NavController,
    likeViewModel: LikeViewModel = viewModel()
) {
    val uiState by likeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        likeViewModel.getMemberInfo()
        likeViewModel.getCheerTempMarkets()
        likeViewModel.getTempMarkets()
    }

    LaunchedEffect(uiState.selectedCategory) {
        likeViewModel.getTempMarkets()
    }

    LaunchedEffect(uiState.searchKey) {
        if (uiState.searchKey.isNotEmpty()) {
            likeViewModel.searchTempMarket(uiState.searchKey)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                LikeMarketSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    key = uiState.searchKey
                ) { likeViewModel.onSearchKeyChanged(it) }
            }
            if (uiState.searchKey.isEmpty()) {
                item {
                    MyHeartCount(uiState.cheerTicket)
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
                    SpaceTitle(title = "달성 임박", badgeTitle = "HOT 🔥")
                    Spacer(modifier = Modifier.height(12.dp))
                }
                item {
                    if (uiState.cheerTempMarkets.isEmpty()) {
                        EmptyMessage()
                    } else {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_PADDING),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(items = uiState.cheerTempMarkets) { tempMarket ->
                                RequestCard(
                                    modifier = Modifier.width(284.dp),
                                    marketName = tempMarket.marketName,
                                    likeCount = tempMarket.cheerCount,
                                    thumbnail = NetworkModule.getImage(tempMarket.thumbnail, isTempMarket = true),
                                    isMyDone = tempMarket.isCheer,
                                    isRequestDone = tempMarket.dueDate == 0,
                                    duDate = tempMarket.dueDate,
                                    onCheer = { likeViewModel.cheer(tempMarket.marketId) }
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    HorizontalDivider(color = Color(0xffeeeeee), thickness = 4.dp)
                    Spacer(modifier = Modifier.height(20.dp))
                    SpaceTitle(title = "지금 공감하면 할인권을 드려요", badgeTitle = "EVENT")
                    Spacer(modifier = Modifier.height(20.dp))
                    CategorySelector(
                        selectedCategory = uiState.selectedCategory,
                        onChange = { likeViewModel.onCategoryChanged(it) }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                if (uiState.tempMarkets.isNotEmpty()) {
                    items(uiState.tempMarkets.size / 2) { index ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = PAGE_HORIZONTAL_PADDING,
                                    end = PAGE_HORIZONTAL_PADDING,
                                    bottom = 10.dp
                                ),
                            horizontalArrangement = Arrangement.spacedBy(11.dp)
                        ) {
                            val market1 = uiState.tempMarkets[index * 2]
                            RequestCard(
                                modifier = Modifier
                                    .weight(1f),
                                marketName = market1.marketName,
                                likeCount = market1.cheerCount,
                                thumbnail = NetworkModule.getImage(market1.thumbnail, isTempMarket = true),
                                isMyDone = market1.isCheer,
                                isRequestDone = market1.dueDate == 0,
                                duDate = market1.dueDate,
                                onCheer = { likeViewModel.cheer(market1.marketId) }
                            )

                            if (index * 2 + 1 < uiState.tempMarkets.size) {
                                val market2 = uiState.tempMarkets[index * 2 + 1]
                                RequestCard(
                                    modifier = Modifier
                                        .weight(1f),
                                    marketName = market2.marketName,
                                    likeCount = market2.cheerCount,
                                    thumbnail = NetworkModule.getImage(market2.thumbnail, isTempMarket = true),
                                    isMyDone = market2.isCheer,
                                    isRequestDone = market2.dueDate == 0,
                                    duDate = market2.dueDate,
                                    onCheer = { likeViewModel.cheer(market2.marketId) }
                                )
                            } else {
                                Box(Modifier.weight(1f))
                            }
                        }
                    }
                } else {
                    item {
                        EmptyMessage(message = "등록된 매장이 없습니다.")
                    }
                }
            } else {
                items(uiState.searchTempMarket) { tempMarket ->
                    Text(tempMarket.toString())
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
private fun MyHeartCount(tiekctCount: Int) {
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
            Text(text = "${tiekctCount}개", fontWeight = FontWeight.Bold, fontSize = 16.sp)
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
