package dev.kichan.marketplace

import android.graphics.drawable.Icon
import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kichan.marketplace.ui.theme.Gray_3
import dev.kichan.marketplace.ui.theme.Gray_6
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainPage(navController: NavController) {
    val items = mapOf(
        Page.Home to Icons.Filled.Home,
        Page.Category to Icons.Filled.ShoppingCart,
        Page.Map to Icons.Filled.Place,
        Page.My to Icons.Filled.Person,
    )

    Text(text = "dsd")
}

//@Composable
//fun BottomNav(navController: NavHostController, items: Map<Page, ImageVector>) {
//    BottomNavigation {
//        val currentRoute = currentBackStackEntryAsState(navController).value?.destination?.route
//        items.forEach { screen ->
//            BottomNavigationItem(
//                icon = { Icon(screen.value, contentDescription = screen.key.name) },
//                label = { Text(screen.key.name) },
//                selected = currentRoute == screen.key,
//                onClick = {
//                    navController.navigate(screen.key.name) {
//                        popUpTo(navController.graph.startDestinationId) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                selectedContentColor = Gray_6,
//                unselectedContentColor = Gray_3
//            )
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun BottomSheetPreview() {
//    MarketPlaceTheme {
//        BottomNav(rememberNavController(), mapOf())
//    }
//}


//@Preview(showBackground = true)
//@Composable
//fun MainPagePreview() {
//    MarketPlaceTheme {
//        MainPage(rememberNavController())
//    }
//}
