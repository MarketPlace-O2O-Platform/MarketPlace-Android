package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.ui.Page

@Composable
fun BottomNavigationBar(navController: NavController, pageList: List<Pair<Page, ImageVector>>) {
    val selectedIndex = remember { mutableStateOf(0) }

    val selectedContentColor = Color(0xff545454)
    val unselectedContentColor = Color(0xffC7C7C7)

    val bottomNavShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val itemModifier = Modifier.padding(vertical = 6.dp)

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .clip(bottomNavShape) // 둥근 모서리 설정
            .border(width = 1.dp, color = Color(0xffe1e1e1), shape = bottomNavShape)
    ) {
        pageList.mapIndexed { index, item ->
            val page = item.first
            val icon = item.second

            BottomNavigationItem(
                icon = { Icon(icon, contentDescription = page.name) },
                label = { Text(page.name.uppercase() ) }, // 문자열.uppercase() : 문자열을 대문자로 바꿈
                selected = selectedIndex.value == index,
                onClick = {
                    selectedIndex.value = index
                    navController.navigate(page.name)
                },
                selectedContentColor = selectedContentColor,
                unselectedContentColor = unselectedContentColor,
                modifier = itemModifier,
            )
        }
    }
}