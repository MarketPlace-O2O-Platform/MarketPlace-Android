package dev.kichan.marketplace.ui.component.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.common.LargeCategory
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.Page

@Composable
fun CategorySelector(navController: NavController) {
    val categories = LargeCategory.entries

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_HORIZONTAL_PADDING),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        categories.chunked(4).forEach { rowItems ->
            Row(
                Modifier.fillMaxWidth().padding(7.dp),
                horizontalArrangement = Arrangement.SpaceBetween // 각 항목 간 간격을 균등하게 설정
            ) {
                rowItems.forEach { category ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally, // 버튼과 텍스트를 가운데 정렬
                        modifier = Modifier.clickable {
//                            navController.navigate("${Page.CategoryEventList.name}/${category.name}")
                        }
                    ) {
                        Image(
                            painter = painterResource(id = category.icon),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = category.nameKo)
                    }
                }
            }
        }
    }
}