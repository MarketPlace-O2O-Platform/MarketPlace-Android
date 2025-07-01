package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import dev.kichan.marketplace.ui.component.atoms.CustomButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.market.MarketRes
import dev.kichan.marketplace.model.repository.MarketRepository
import dev.kichan.marketplace.ui.component.atoms.SearchResultItem
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SearchPage(modifier: Modifier = Modifier) {
    val marketRepository = MarketRepository()
    var key by remember { mutableStateOf<String>("") }
    var recentKeywords by remember {
        mutableStateOf(
            listOf(
                "신복관",
                "송쭈집",
                "우정소갈비",
                "디저트39",
                "헬스장",
                "필라테스"
            )
        )
    }
    var isFirst by remember { mutableStateOf(true) }
    var result by remember {
        mutableStateOf<List<MarketRes>>(listOf())
    }

    val onSearch: (String) -> Unit = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = marketRepository.searchMarket(it, null, 20)
            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    val _result = res.body()?.response?.marketResDtos ?: listOf()
                    result = _result

                    if (_result.isNotEmpty()) {
                        isFirst = false
                    }
                }
            }
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SearchBar(key, {
                key = it

                if (it.length >= 2) {
                    onSearch(it)
                }
            })

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xff000000))
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (result.isNotEmpty()) {
                LazyColumn {
                    items(items = result) {
                        SearchResultItem(
                            title = it.name,
                            description = it.description,
                            imageUrl = NetworkModule.getImage(id = it.thumbnail)
                        )
                    }
                }
            } else if (key.isBlank() || isFirst) {
                RecentKeyword(recentKeywords)
                PopularBenefitsList()
            } else {
                SearchResultEmpty()
            }
        }
    }
}

@Composable
private fun PopularBenefitsList() {
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(4) {
            BenefitCard()
        }
    }
}

@Composable
private fun RecentKeyword(
    keywords: List<String>
) {
    Text(
        text = "최근 검색어",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = keywords) { keyword ->
            Chip(text = keyword)
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    onChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 12.dp)
            .padding(start = 9.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.left_black),
            contentDescription = "Back",
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .padding(1.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))

        BasicTextField(
            value = value,
            onValueChange = { onChange(it) },
            maxLines = 1,
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
fun Chip(text: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, Color(0xFFC6C6C6)),
        modifier = Modifier
            .padding(start = 12.dp, top = 6.dp, bottom = 6.dp)
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
fun BenefitCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),

        modifier = Modifier
            .width(200.dp)
            .padding(start = 20.dp, end = 8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.brown),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(172.dp)
                        .fillMaxWidth()
                )
                Image(
                    painter = painterResource(id = R.drawable.bookmark2), // 스크랩 기능 아이콘
                    contentDescription = "Bookmark",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                )
            }
            Text(
                text = "콜드케이스 인하대점",
                fontSize = 10.sp,
                lineHeight = 13.51.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                modifier = Modifier.padding(top = 11.39.dp, bottom = 4.dp)
            )
            Text(
                text = "방탈출카페 2인 이용권",
                fontSize = 15.sp,
                lineHeight = 19.66.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
            )
        }
    }
}

@Composable
fun SearchResultEmpty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffeeeee)),
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

@Preview(showBackground = true)
@Composable
private fun SearchPagePrev() {
    MarketPlaceTheme {
        SearchPage()
    }
}