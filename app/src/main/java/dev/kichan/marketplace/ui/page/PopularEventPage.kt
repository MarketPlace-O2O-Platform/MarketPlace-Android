package dev.kichan.marketplace.ui.page

import LargeCategory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.data.category.Category
import dev.kichan.marketplace.ui.component.CategoryTap
import dev.kichan.marketplace.ui.component.EventListItem
import dev.kichan.marketplace.ui.component.NavAppBar
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun PopularEventPage(navController: NavController) {
    var selectedCategory by remember { mutableStateOf(LargeCategory.Food) }

    Scaffold(
        topBar = {
            NavAppBar(title = "요즘 많이 찾는 제휴 이벤트") {
                navController.popBackStack()
            }
        }
    ) {
        Column(Modifier.padding(it)) {
            CategoryTap(selectedCategory = selectedCategory, onSelected = { selectedCategory = it })
            LazyColumn {
                items(50) {
                    EventListItem(
                        imageRes = R.drawable.desert,
                        title = "참피온삼겹살 트리플 스트리트점",
                        couponDescription = "방탈출 카페 2인 이용권\n스머프와 함께 즐기는 미디어아트 보드게임!",
                        location = "송도",
                        likes = 440,
                        category = "음식&주점",
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PopularEventPagePreview() {
    MarketPlaceTheme {
        LikePage(rememberNavController())
    }
}