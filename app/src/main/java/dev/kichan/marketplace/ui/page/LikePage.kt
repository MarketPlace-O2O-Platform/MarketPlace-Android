package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.dto.TempMarketRes
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.atoms.CategorySelector
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.component.atoms.EmptyMessage
import dev.kichan.marketplace.ui.component.atoms.LikeMarketSearchBar
import dev.kichan.marketplace.ui.component.molecules.CheerRequestCard
import dev.kichan.marketplace.ui.component.molecules.TempMarketCard
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.ui.viewmodel.LikeViewModel

@Composable
fun LikePage(
    navController: NavController,
    likeViewModel: LikeViewModel = viewModel()
) {
    val uiState by likeViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        likeViewModel.snackbarMessage.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

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

    val loadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems > 0 && lastVisibleItemIndex >= totalItems - 5
        }
    }

    LaunchedEffect(loadMore) {
        if (loadMore) {
            likeViewModel.loadMoreTempMarkets()
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = listState,
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
                                CheerRequestCard(
                                    modifier = Modifier.width(284.dp),
                                    marketName = tempMarket.marketName,
                                    thumbnail = NetworkModule.getImage(
                                        tempMarket.thumbnail,
                                        isTempMarket = true
                                    ),
                                    dueDate = tempMarket.dueDate,
                                    cheerCount = tempMarket.cheerCount,
                                    isCheer = tempMarket.isCheer,
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
                            TempMarketCard(
                                modifier = Modifier
                                    .weight(1f),
                                marketName = market1.marketName,
                                cheerCount = market1.cheerCount,
                                thumbnail = NetworkModule.getImage(
                                    market1.thumbnail,
                                    isTempMarket = true
                                ),
                                isCheer = market1.isCheer,
                                dueDate = market1.dueDate,
                                onCheer = { likeViewModel.cheer(market1.marketId) }
                            )

                            if (index * 2 + 1 < uiState.tempMarkets.size) {
                                val market2 = uiState.tempMarkets[index * 2 + 1]
                                TempMarketCard(
                                    modifier = Modifier
                                        .weight(1f),
                                    marketName = market2.marketName,
                                    cheerCount = market2.cheerCount,
                                    thumbnail = NetworkModule.getImage(
                                        market2.thumbnail,
                                        isTempMarket = true
                                    ),
                                    isCheer = market2.isCheer,
                                    dueDate = market2.dueDate,
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
                if (uiState.searchTempMarket.isEmpty()) {
                    item { SearchEmptyContent { navController.navigate(Page.RequestPage.name) } }
                } else {
                    items(uiState.searchTempMarket) { tempMarket ->
                        MarketCard(
                            tempMarket
                        ) { likeViewModel.cheer(tempMarket.marketId) }
                    }
                }
            }
        }
    }
}

@Composable
fun SpaceTitle(title: String, badgeTitle: String) {
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

@Composable
fun MarketCard(
    market: TempMarketRes,
    onCheerClick: (Long) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = NetworkModule.getImageModel(
                context,
                NetworkModule.getImage(market.thumbnail, isTempMarket = true)
            ),
            contentDescription = market.marketName,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = market.marketName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = market.marketDescription,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                market.isCheer -> {
                    CustomButton(
                        text = "공감 완료",
                        isDisable = true,
                        modifier = Modifier.fillMaxWidth()
                    ) { }
                }
                else -> {
                    CustomButton(
                        text = "공감하기",
                        icon = painterResource(R.drawable.empty_heart),
                        backgroundColor = Color(0xFF4A4A4A),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        onCheerClick(market.marketId)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchEmptyContent(
    modifier: Modifier = Modifier,
    onRequest: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "검색 결과가 없어요. 찾으시는 매장이 없으신가요?",
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(500),
            fontSize = 14.sp,
            color = Color(0xff5E5E5E),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 28.dp)
        )
        Spacer(modifier = Modifier.height(72.dp))
        Text(
            "매장 요청하기를 해보세요!",
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(600),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
        Image(
            painter = painterResource(R.drawable.ic_undraw_online_stats_0g94_1),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomButton("요청하기", Modifier.width(240.dp)) { onRequest() }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchEmptyContentPreview() {
    MarketPlaceTheme {
        SearchEmptyContent {}
    }
}