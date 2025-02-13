package dev.kichan.marketplace.ui.page.searchresult

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.PretendardFamily

class SearchResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchResultUI()
        }
    }
}

@Composable
fun SearchResultUI() {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchHeader()
        Spacer(modifier = Modifier.height(20.dp))
        SearchResultList()
    }
}

@Composable
fun SearchHeader() {
    Column(modifier = Modifier.fillMaxWidth()) {
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
            SearchBar()
        }
        Divider(color = Color(0xFF303030), thickness = 1.dp)
    }
}

@Composable
fun SearchBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(color = Color(0xFFFAFAFA), shape = RoundedCornerShape(size = 50.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = R.drawable.search2),
            contentDescription = "Search",
            modifier = Modifier
                .width(18.dp)
                .height(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "가고 싶은 매장을 찾아보세요",
            fontSize = 12.sp,
            color = Color(0xFFB0B0B0),
        )
    }
}

@Composable
fun SearchResultList() {
    val searchResults = listOf(
        "참피온삼겹살 헤어샵" to "맛있는 삼겹살맛있는\n삼겹살맛있는 삼겹살",
        "참피온삼겹살 헤어샵" to "맛있는 삼겹살맛있는\n삼겹살맛있는 삼겹살"
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        items(searchResults) { result ->
            SearchResultItem(title = result.first, description = result.second)
            Box(modifier = Modifier.fillMaxWidth()) {
                Divider(color = Color(0xFFDDDDDD), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun SearchResultItem(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White) // 배경 추가
        ) {
            Image(
                painter = painterResource(id = R.drawable.brown),
                contentDescription = null,
                modifier = Modifier
                    .width(110.dp)
                    .height(110.dp)
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF333333),
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp).fillMaxWidth()
            )
            Text(
                text = description,
                fontSize = 13.sp,
                lineHeight = 18.2.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF7D7D7D),
                modifier = Modifier.padding(start = 16.dp).fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchResultUI() {
    SearchResultUI()
}
