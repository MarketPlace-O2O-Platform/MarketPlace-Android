package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.ui.Page

@Composable
fun BottomNavigationBar(navController: NavController, pageList: List<Pair<Page, ImageVector>>) {
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    val selectedContentColor = Color(0xff545454)
    val unselectedContentColor = Color(0xffC7C7C7)

    val bottomNavShape = RoundedCornerShape(0.dp)
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
                label = { Text(page.pageName) },
                selected = navController.currentDestination?.route == page.name,
                onClick = {
                    selectedIndex = index
                    navController.navigate(page.name)
                },
                selectedContentColor = selectedContentColor,
                unselectedContentColor = unselectedContentColor,
                modifier = itemModifier,
            )
        }
    }
}