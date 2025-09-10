package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.kichan.marketplace.ui.Page
import kotlinx.coroutines.delay

@Composable
fun SplashPage(
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        delay(1000) // Simulate a splash screen delay
        navController.navigate(Page.Home.name) {
            popUpTo(Page.Splash.name) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
