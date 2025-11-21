package dev.kichan.marketplace.ui.page

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.kichan.marketplace.BuildConfig
import dev.kichan.marketplace.model.dto.NotificationRes
import dev.kichan.marketplace.ui.component.atoms.NavAppBar
import dev.kichan.marketplace.ui.theme.PretendardFamily
import dev.kichan.marketplace.viewmodel.AlertViewModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun AlertPage(
    navController: NavController,
    viewModel: AlertViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            NavAppBar("알림") { navController.popBackStack() }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            NotificationFilterBar(
                selectedFilter = NotificationType.fromServerType(uiState.filterType),
                onFilterSelected = { viewModel.setFilterType(it.serverType) },
                onMarkAllRead = { viewModel.allRead() }
            )
            if (uiState.isLoading && uiState.notifications.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(uiState.error.toString())
                }
            } else if (uiState.notifications.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("알림이 없습니다.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    items(uiState.notifications) {
                        NotificationItem(
                            notification = it,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationFilterBar(
    selectedFilter: NotificationType,
    onFilterSelected: (NotificationType) -> Unit,
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
            val filters = NotificationType.values()
            items(filters.size) { index ->
                val filter = filters[index]
                val isSelected = selectedFilter == filter

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (isSelected) Color(0xff303030) else Color.Transparent,
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Black else Color(0xffC6C6C6),
                            shape = RoundedCornerShape(50)
                        )
                        .clickable { onFilterSelected(filter) }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = filter.displayName,
                        color = if (isSelected) Color.White else Color(0xff5E5E5E),
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                    )
                }
            }
        }

        // 오른쪽 전체 읽음 버튼
        Row {
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(16.dp)
                    .background(
                        color = Color(0xffE0E0E0)
                    )
            )
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

fun formatCreatedAt(createdAt: String?): String {
    if (createdAt == null || createdAt.length < 26) {
        return "알 수 없음"
    }

    return try {
        val now = LocalDateTime.now()
        val then = LocalDateTime.parse(createdAt.substring(0, 26))

        val diff = ChronoUnit.SECONDS.between(then, now)

        when {
            diff < 60 -> "방금 전"
            diff < 3600 -> "${diff / 60}분 전"
            diff < 86400 -> "${diff / 3600}시간 전"
            diff < 2592000 -> "${diff / 86400}일 전"
            else -> then.toLocalDate().toString()
        }
    } catch (e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.e("AlertPage", "createdAt 파싱 실패: $createdAt", e)
        }
        "알 수 없음"
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
        Text(
            text = NotificationType.fromServerType(notification.targetType).displayName,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight.W400,
            fontSize = 10.sp,
            color = Color(0xff5E5E5E),
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(50)
                )
                .border(width = 1.dp, color = Color(0xffEEEEEE), shape = RoundedCornerShape(50))
                .padding(horizontal = 8.dp, vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = notification.title,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
        )

        Spacer(modifier = Modifier.height(2.dp))

        // 내용
        Text(
            text = notification.body,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            color = Color(0xff868686)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 생성 시간
        Text(
            text = formatCreatedAt(notification.createdAt),
            fontFamily = PretendardFamily,
            fontWeight = FontWeight.W500,
            fontSize = 13.sp,
            color = Color(0xff9b9b9b)
        )
    }
}
