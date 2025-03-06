package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.component.atoms.CouponListItemWithBookmark
import dev.kichan.marketplace.ui.component.atoms.NavAppBar

@Composable
fun EventListPage(navController: NavController, title: String) {
    Scaffold(
        topBar = {
            NavAppBar(title = title) {
                navController.popBackStack()
            }
        }
    ) {
        Column(Modifier.padding(it)) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
            ) {
                items(50) {
                    CouponListItemWithBookmark(
                        title = "참피온삼겹살 트리플 스트리트점",
                        couponDescription = "방탈출 카페 2인 이용권\n스머프와 함께 즐기는 미디어아트 보드게임!",
                        location = "송도",
                        likes = 440,
                        category = "음식&주점",
                        modifier = Modifier,
                        thumbnail = ""
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xffEAEAEA))
                            .height(1.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}