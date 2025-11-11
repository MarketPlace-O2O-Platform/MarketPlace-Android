package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.javafaker.Faker
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.component.atoms.SearchResultItem

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
//            SearchBar()
        }
        Divider(color = Color(0xFF303030), thickness = 1.dp)
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
            SearchResultItem(title = result.first, description = result.second, imageUrl = Faker().avatar().image())
            Box(modifier = Modifier.fillMaxWidth()) {
                Divider(color = Color(0xFFDDDDDD), thickness = 1.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchResultUI() {
    SearchResultUI()
}
