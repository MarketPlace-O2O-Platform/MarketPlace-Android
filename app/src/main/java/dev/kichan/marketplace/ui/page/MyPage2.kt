import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kichan.marketplace.ui.bottomNavItem
import dev.kichan.marketplace.ui.component.atoms.BottomNavigationBar
import dev.kichan.marketplace.ui.component.ProfileHeader
import dev.kichan.marketplace.viewmodel.LoginUiState
import dev.kichan.marketplace.viewmodel.LoginViewModel
import dev.kichan.marketplace.viewmodel.MyViewModel

@Composable
fun MyPage2(
    navController: NavController,
    authViewModel: LoginViewModel = LoginViewModel(),
    myViewModel: MyViewModel = MyViewModel(),
) {
    val authState = authViewModel.loginState
    val onLogout = {}

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, pageList = bottomNavItem)
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item { Spacer(modifier = Modifier.height(21.dp)) }
            item {
                ProfileHeader(
                    navController = navController,
                    member = (authState as LoginUiState.Success).member,
                    onLogout = onLogout
                )
            }
            item {
                Spacer(modifier = Modifier.height(2.dp))
                Divider(
                    color = Color(0xFFF4F4F4),
                    thickness = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MyPage2Preview() {
//    MarketPlaceTheme() {
//        MyPage2()
//    }
//}