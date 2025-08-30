package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kichan.marketplace.model.dto.NotificationRes
import dev.kichan.marketplace.viewmodel.AlertViewModel

@Composable
fun AlertPage(
    viewModel: AlertViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            Text("로딩 중...")
        } else if (uiState.error != null) {
            Text(text = uiState.error!!)
        } else {
            Text(text = "알림 ${uiState.notifications.size}개를 받았습니다. UI를 구현해주세요.")
            LazyColumn {
                items(uiState.notifications) {
                    NotificationItem(
                        notification = it,
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: NotificationRes,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        // 상단 카테고리 (예: "쿠폰 발급")
        Text(
            text = "쿠폰 발급",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 타이틀
        Text(
            text = notification.title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(2.dp))

        // 내용
        Text(
            text = notification.body,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 생성 시간
        Text(
            text = notification.createdAt, // "1일 전" 같은 포맷으로 변환 필요
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
