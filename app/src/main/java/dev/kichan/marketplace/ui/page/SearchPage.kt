package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.kichan.marketplace.R
import dev.kichan.marketplace.common.noRippleClickable
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import dev.kichan.marketplace.ui.component.atoms.SearchResultItem
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.ui.viewmodel.PopularCoupon
import dev.kichan.marketplace.ui.viewmodel.SearchViewModel

@Composable
fun SearchPage(
    navController: NavController,
    viewModel: SearchViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getPopularCoupons()
    }

    Scaffold(
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SearchBar(
                value = uiState.searchKey,
                onChange = {
                    viewModel.onSearchKeyChanged(it)
                },
                onBack = { navController.popBackStack() },
                onSearch = { viewModel.search() }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xff000000))
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (uiState.searchResults.isNotEmpty()) {
                LazyColumn {
                    items(items = uiState.searchResults) {
                        SearchResultItem(
                            title = it.marketName,
                            description = it.marketDescription,
                            imageUrl = NetworkModule.getImage(it.thumbnail),
                            onClick = {
                                navController.navigate("${Page.EventDetail.name}/${it.marketId}")
                            }
                        )
                    }
                }
            } else if (uiState.searchKey.isBlank() || uiState.isFirstSearch) {
                RecentKeyword(
                    keywords = uiState.recentKeywords,
                    onKeywordClick = {
                        viewModel.onSearchKeyChanged(it)
                        viewModel.search()
                    },
                    onClearClick = {
                        viewModel.clearRecentKeywords()
                    }
                )
                PopularBenefitsList(
                    coupons = uiState.popularCoupons,
                    onCardClick = { marketId ->
                        navController.navigate("${Page.EventDetail.name}/${marketId}")
                    }
                )
            } else {
                SearchResultEmpty()
            }
        }
    }
}

@Composable
private fun PopularBenefitsList(
    coupons: List<PopularCoupon>,
    onCardClick: (Long) -> Unit
) {
    Text(
        text = "인기 혜택",
        fontSize = 15.sp,
        lineHeight = 26.sp,
        fontFamily = PretendardFamily,
        fontWeight = FontWeight(600),
        color = Color(0xFF000000),
        modifier = Modifier.padding(start = 21.dp, top = 48.dp, bottom = 10.dp)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, end = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(coupons) { coupon ->
            BenefitCard(
                coupon = coupon,
                onClick = { onCardClick(coupon.marketId) }
            )
        }
    }
}

@Composable
private fun RecentKeyword(
    keywords: List<String>,
    onKeywordClick: (String) -> Unit,
    onClearClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "최근 검색어",
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 26.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF121212),
            )
        )
        Text(
            text = "지우기",
            fontSize = 14.sp,
            color = Color(0xFF5E5E5E),
            modifier = Modifier.noRippleClickable { onClearClick() }
        )
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = keywords) { keyword ->
            Chip(text = keyword, onClick = { onKeywordClick(keyword) })
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    onChange: (String) -> Unit,
    onBack: () -> Unit,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 12.dp)
            .padding(start = 9.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBack() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.left_black),
                contentDescription = "Back",
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(1.dp)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))

        BasicTextField(
            value = value,
            onValueChange = { onChange(it) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        ) { innerTextField ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(end = 12.dp)
                    .background(
                        color = Color(0xFFFAFAFA),
                        shape = RoundedCornerShape(size = 50.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.search2),
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(start = 11.dp, end = 9.dp, top = 7.dp, bottom = 7.dp)
                        .width(18.dp)
                        .height(18.dp)
                        .clickable { onSearch() }
                )

                Image(
                    painter = painterResource(id = R.drawable.i),
                    contentDescription = "I Icon",
                    modifier = Modifier
                        .border(width = 1.dp, color = Color(0xFFC6C6C6))
                        .padding(1.dp)
                        .width(0.dp)
                        .height(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isBlank()) {
                        Text(
                            text = "가고 싶은 매장을 찾아보세요",
                            fontSize = 12.sp,
                            lineHeight = 26.sp,
                            fontFamily = PretendardFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFB0B0B0),
                        )
                    }
                    innerTextField()
                }
            }
        }
    }
}

@Composable
fun Chip(text: String, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, Color(0xFFC6C6C6)),
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            lineHeight = 21.sp,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(400),
            color = Color(0xFF5E5E5E),
            modifier = Modifier.padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 6.dp)
        )
    }
}

@Composable
fun BenefitCard(coupon: PopularCoupon, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = NetworkModule.getImageModel(
                LocalContext.current,
                NetworkModule.getImage(coupon.thumbnail)
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(172.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = coupon.marketName,
            fontSize = 10.sp,
            lineHeight = 13.51.sp,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(600),
            color = Color(0xFF000000),
            modifier = Modifier.padding(top = 11.39.dp, bottom = 4.dp)
        )
        Text(
            text = coupon.couponName,
            fontSize = 15.sp,
            lineHeight = 19.66.sp,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(500),
            color = Color(0xFF000000),
        )
    }
}

@Composable
fun SearchResultEmpty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "검색 결과가 없어요.\n찾으시는 매장이 없으신가요?",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF5E5E5E),
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(26.dp))
        Text(
            text = "매장 요청하기를 해보세요!",
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 22.5.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF303030),
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(45.dp))
        Image(painter = painterResource(R.drawable.img_online_stats), null)
        Spacer(modifier = Modifier.height(31.dp))
        CustomButton("요청하기", modifier = Modifier.width(240.dp)) { }
    }
}