package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.viewmodel.SplashUiState
import dev.kichan.marketplace.viewmodel.SplashViewModel

@Composable
fun SplashPage(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        when (uiState) {
            is SplashUiState.Authenticated -> {
                navController.navigate(Page.Home.name) {
                    popUpTo(Page.Splash.name) { inclusive = true }
                }
            }
            is SplashUiState.Unauthenticated -> {
                navController.navigate(Page.Login.name) {
                    popUpTo(Page.Splash.name) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
