package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.viewmodel.LoginViewModel
import dev.kichan.marketplace.viewmodel.LoginUiState

@Composable
fun SplashPage(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val state = loginViewModel.loginState

    when (state) {
        is LoginUiState.Error -> {
            navController.popBackStack()
            navController.navigate(Page.Login.name)
        }
        LoginUiState.Idle -> {}
        LoginUiState.Loading -> {}
        is LoginUiState.Success -> {
            navController.popBackStack()
            navController.navigate(Page.Main.name)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(R.drawable.logo), contentDescription = null)
        }
    }
}