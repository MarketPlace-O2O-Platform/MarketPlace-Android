package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kichan.marketplace.common.AnnouncementData
import dev.kichan.marketplace.common.RemoteConfigManager
import dev.kichan.marketplace.ui.AnnouncementDialog
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.viewmodel.SplashUiState
import dev.kichan.marketplace.viewmodel.SplashViewModel

@Composable
fun SplashPage(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var announcement by remember { mutableStateOf<AnnouncementData?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var configLoaded by remember { mutableStateOf(false) }

    // Remote Config fetch
    LaunchedEffect(Unit) {
        RemoteConfigManager.fetchAndActivate()
        val data = RemoteConfigManager.getAnnouncement()
        if (data.shouldShow()) {
            announcement = data
            showDialog = true
        }
        configLoaded = true
    }

    // 네비게이션 로직
    LaunchedEffect(uiState, showDialog, configLoaded) {
        // Config 로딩 완료 + Dialog 닫힌 상태에서만 네비게이션
        if (configLoaded && !showDialog) {
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
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 공지가 닫기 불가능(dismissible=false)이 아닐 때만 로딩 표시
        if (announcement?.dismissible != false) {
            CircularProgressIndicator()
        }

        // 공지 Dialog
        if (showDialog && announcement != null) {
            AnnouncementDialog(
                data = announcement!!,
                onDismiss = {
                    if (announcement!!.dismissible) {
                        showDialog = false
                    }
                }
            )
        }
    }
}
