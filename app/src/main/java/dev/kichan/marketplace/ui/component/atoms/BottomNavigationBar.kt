package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.ui.Page


@Composable
fun BottomNavigationBar(navController: NavController, pageList: List<Pair<Page, Int>>) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    val selectedContentColor = Color(0xff545454)
    val unselectedContentColor = Color(0xffC7C7C7)

    Column {
        // 상단 구분선만 추가
        Divider(
            color = Color(0xffe1e1e1),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )

        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.Gray,
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            pageList.mapIndexed { index, item ->
                val page = item.first
                val icon = item.second

                BottomNavigationItem(
                    icon = { Icon(painterResource(id = icon), contentDescription = page.name) },
                    label = { Text(page.pageName) },
                    selected = navController.currentDestination?.route == page.name,
                    onClick = {
                        selectedIndex = index
                        navController.navigate(page.name) {
                            // 하단바는 스택을 쌓지 않고 Home까지 비우기
                            popUpTo(Page.Home.name) {
                                saveState = true  // 각 탭의 상태 저장
                            }
                            launchSingleTop = true  // 같은 화면 중복 방지
                            restoreState = true  // 탭 복귀 시 상태 복원
                        }
                    },
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor,
                )
            }
        }
    }
}