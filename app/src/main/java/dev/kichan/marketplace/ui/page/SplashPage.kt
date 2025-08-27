package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.viewmodel.LoginViewModel
import dev.kichan.marketplace.viewmodel.LoginUiState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SplashPage(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel(),
) {
    val state by loginViewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        loginViewModel.checkLoginStatus()
    }

    LaunchedEffect(state) {
        when (state) {
            is LoginUiState.Error -> {
                navController.popBackStack()
                navController.navigate(Page.Login.name)
            }

            LoginUiState.Authenticated -> {
                navController.popBackStack()
                navController.navigate(Page.Main.name)
            }

            LoginUiState.Unauthenticated -> {
                navController.popBackStack()
                navController.navigate(Page.Login.name)
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(R.drawable.logo), contentDescription = null)
    }
}