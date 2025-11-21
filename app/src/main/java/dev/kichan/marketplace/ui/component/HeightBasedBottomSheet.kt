package dev.kichan.marketplace.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * 높이 기반 바텀 시트 - offset 대신 height를 변경
 * 앱 하단바 위에서 시작하도록 보장
 *
 * Nested Scroll 지원:
 * - 접힌 상태: 어디든 위로 스크롤하면 시트 펼침
 * - 펼친 상태: 리스트가 맨 위일 때만 아래로 스크롤 시 시트 접힘
 */
@Composable
fun HeightBasedBottomSheet(
    modifier: Modifier = Modifier,
    heights: List<Dp>, // 다단계 높이 리스트
    currentStage: Int = 0, // 외부에서 제어 가능한 현재 단계
    onStageChange: (Int) -> Unit = {},
    listState: LazyListState? = null, // LazyColumn의 스크롤 상태
    content: @Composable (currentStage: Int) -> Unit
) {
    // 애니메이션 진행 상태 추적
    var isAnimating by remember { mutableStateOf(false) }

    LaunchedEffect(currentStage) {
        isAnimating = true
        delay(500)  // 애니메이션 시간 + 버퍼 (사용자 제스처 완료 대기)
        isAnimating = false
    }

    val animatedHeight by animateDpAsState(
        targetValue = heights[currentStage],
        animationSpec = tween(durationMillis = 300),
        label = "sheet_height"
    )

    // Nested Scroll 연결
    val nestedScrollConnection = remember(currentStage, listState, isAnimating) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y

                return when {
                    // 애니메이션 중이거나 접힌 상태: 모든 스크롤 차단
                    isAnimating || currentStage == 0 -> {
                        if (delta < 0 && currentStage == 0) {
                            // 위로 스크롤 → 시트 펼침
                            onStageChange(heights.size - 1)
                        }
                        available  // 모든 스크롤 이벤트 소비 (리스트는 스크롤 안 됨)
                    }
                    // 펼친 상태: 리스트가 맨 위일 때만 시트 제어
                    currentStage == heights.size - 1 && delta > 0 -> {
                        val isAtTop = listState?.let {
                            it.firstVisibleItemIndex == 0 && it.firstVisibleItemScrollOffset == 0
                        } ?: false

                        if (isAtTop) {
                            onStageChange(0)
                            available  // 스크롤 이벤트 소비
                        } else {
                            Offset.Zero  // 리스트 스크롤 허용
                        }
                    }
                    else -> Offset.Zero  // 리스트 스크롤 허용
                }
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                return if (isAnimating || currentStage == 0) {
                    available  // Fling 차단 (관성 스크롤 차단)
                } else {
                    Velocity.Zero  // Fling 허용
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(animatedHeight)
            .nestedScroll(nestedScrollConnection)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 핸들바 (시각적 요소)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(34.dp)
                        .height(5.dp)
                        .background(Color(0xffc7c7c7), RoundedCornerShape(12.dp))
                )
            }

            // 컨텐츠 영역 (LazyColumn 등)
            Box(modifier = Modifier.weight(1f)) {
                content(currentStage)
            }
        }
    }
}
