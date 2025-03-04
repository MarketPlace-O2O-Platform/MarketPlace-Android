package dev.kichan.marketplace.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.PretendardFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarketplaceUI()
        }
    }
}

@Composable
fun MarketplaceUI() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical=12.dp)
            .padding(start = 9.dp, end=16.dp),
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
            SearchBar()
        }

        Divider(color = Color(0xFF303030), thickness = 1.dp)

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "최근 검색어",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, bottom=8.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf("신복관", "송쭈집", "우정소갈비", "디저트39", "헬스장", "필라테스")) { keyword ->
                Chip(text = keyword)
            }
        }
        Text(
            text = "인기 혜택",
            fontSize = 15.sp,
            lineHeight = 26.sp,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(600),
            color = Color(0xFF000000),
            modifier = Modifier.padding(start = 21.dp, top=48.dp, bottom = 10.dp)
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
}

@Composable
fun SearchBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(32.dp)
            .padding(end=12.dp)
            .background(color = Color(0xFFFAFAFA), shape = RoundedCornerShape(size = 50.dp)),
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
        Text(
            text = "가고 싶은 매장을 찾아보세요",
            fontSize = 12.sp,
            lineHeight = 26.sp,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(400),
            color = Color(0xFFB0B0B0),
        )
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
        Text(text = text,
            fontSize = 15.sp,
            lineHeight = 21.sp,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(400),
            color = Color(0xFF5E5E5E),
            modifier = Modifier.padding(start = 12.dp, top=6.dp, end=12.dp, bottom=6.dp))
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
                    modifier = Modifier.height(172.dp).fillMaxWidth()
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

@Preview(showBackground = true)
@Composable
fun PreviewMarketplaceUI() {
    MarketplaceUI()
}
