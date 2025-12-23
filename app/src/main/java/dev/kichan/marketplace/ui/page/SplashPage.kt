package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kichan.marketplace.common.MaintenanceChecker
import dev.kichan.marketplace.common.MaintenanceStatus
import dev.kichan.marketplace.ui.MaintenanceDialog
import dev.kichan.marketplace.ui.Page
import dev.kichan.marketplace.viewmodel.SplashUiState
import dev.kichan.marketplace.viewmodel.SplashViewModel

@Composable
fun SplashPage(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 점검 상태 체크
    val maintenanceStatus = remember { MaintenanceChecker.getMaintenanceStatus() }
    val showMaintenanceDialog = remember {
        mutableStateOf(maintenanceStatus != MaintenanceStatus.Normal)
    }

    LaunchedEffect(uiState, showMaintenanceDialog.value) {
        // Dialog가 닫혔고, 점검 중이 아닐 때만 네비게이션
        if (!showMaintenanceDialog.value && maintenanceStatus != MaintenanceStatus.InMaintenance) {
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
        // 점검 중이 아닐 때만 로딩 표시
        if (maintenanceStatus != MaintenanceStatus.InMaintenance) {
            CircularProgressIndicator()
        }

        // 점검 공지 Dialog
        if (showMaintenanceDialog.value) {
            MaintenanceDialog(
                status = maintenanceStatus,
                onDismiss = {
                    // 사전 공지 기간에만 닫기 가능
                    if (maintenanceStatus == MaintenanceStatus.PreNotification) {
                        showMaintenanceDialog.value = false
                    }
                }
            )
        }
    }
}
