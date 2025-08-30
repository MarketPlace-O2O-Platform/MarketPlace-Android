package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kichan.marketplace.model.dto.NotificationRes
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.viewmodel.AlertViewModel

@Composable
fun AlertPage(
    navController: NavController,
    viewModel: AlertViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            NavAppBar("알림") { navController.popBackStack() }
        }
    ) {
        Column {
            NotificationFilterBar(
                selectedFilter = when (uiState.filterType) {
                    "MARKET" -> "쿠폰 발급"
                    "COUPON" -> "쿠폰 만료"
                    "NOTICE" -> "공지"
                    else -> "전체"
                },
                onFilterSelected = {
                    val apiType = when (it) {
                        "전체" -> null
                        "쿠폰 발급" -> "MARKET"
                        "쿠폰 만료" -> "COUPON"
                        "공지" -> "NOTICE"
                        else -> null
                    }
                    viewModel.setFilterType(apiType)
                },
                onMarkAllRead = { viewModel.allRead() }
            )
            if (uiState.error != null) {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(uiState.error.toString())
                }
            }
            else if(uiState.notifications.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("알림이 없습니다.")
                }
            }
            else {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                ) {
                    items(uiState.notifications) {
                        NotificationItem(
                            notification = it,
                        )
                    }

                    if (uiState.isLoading) {
                        items(10) {
                            NotificationItemSkeleton()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationFilterBar(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onMarkAllRead: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 왼쪽 필터 목록 - 수평 스크롤
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f, fill = false)
        ) {
            val filters = listOf("전체", "쿠폰 발급", "쿠폰 만료", "공지")
            items(filters.size) { index ->
                val filter = filters[index]
                val isSelected = selectedFilter == filter

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (isSelected) Color.Black else Color.Transparent,
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Black else Color.Gray,
                            shape = RoundedCornerShape(50)
                        )
                        .clickable { onFilterSelected(filter) }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = filter,
                        color = if (isSelected) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // 오른쪽 전체 읽음 버튼
        Text(
            text = "전체 읽음",
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .clickable { onMarkAllRead() }
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun NotificationItemSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        // 상단 카테고리 자리
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(20.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(50))
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 타이틀 자리
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(18.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(4.dp))

        // 본문 자리
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 시간 표시 자리
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(12.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
        )
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
            .background(color = if (notification.isRead) Color(0xffFAFAFA) else Color.White)
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
