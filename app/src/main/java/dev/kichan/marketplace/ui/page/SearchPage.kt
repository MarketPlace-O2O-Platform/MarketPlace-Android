package dev.kichan.marketplace.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "최근 검색어",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyRow {
            items(listOf("신복관", "송쭈집", "우정소갈비", "디저트39")) { keyword ->
                Chip(text = keyword)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "인기 혜택",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyRow {
            items(2) {
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
            .background(color = Color(0xFFFAFAFA), shape = RoundedCornerShape(size = 50.dp))
    )

    {
        Image(
            painter = painterResource(id = R.drawable.left_black),
            contentDescription = "Back",
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = R.drawable.search2),
            contentDescription = "Search",
            modifier = Modifier
                .padding(0.45986.dp)
                .width(18.dp)
                .height(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
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
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun BenefitCard() {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(end = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.burger), // 이미지 리소스 추가 필요
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(120.dp).fillMaxWidth()
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "코드네임스 안락대점", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "방탈출카페 2인 이용권", fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMarketplaceUI() {
    MarketplaceUI()
}
