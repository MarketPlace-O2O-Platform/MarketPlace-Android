package dev.kichan.marketplace.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 높이 기반 바텀 시트 - offset 대신 height를 변경
 * 앱 하단바 위에서 시작하도록 보장
 * 상단 핸들바만 드래그 가능 (LazyColumn 스크롤과 분리)
 */
@Composable
fun HeightBasedBottomSheet(
    modifier: Modifier = Modifier,
    heights: List<Dp>, // 3단계 높이 리스트
    currentStage: Int = 0, // 외부에서 제어 가능한 현재 단계
    onStageChange: (Int) -> Unit = {},
    content: @Composable (currentStage: Int) -> Unit
) {
    var dragOffset by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current
    val threshold = with(density) { 50.dp.toPx() }

    val animatedHeight by animateDpAsState(
        targetValue = heights[currentStage],
        animationSpec = tween(durationMillis = 300),
        label = "sheet_height"
    )

    val draggableState = rememberDraggableState { delta ->
        dragOffset += delta
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(animatedHeight)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 드래그 가능한 핸들바 컨테이너 (클릭 이벤트 통과)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                // 실제 드래그 가능한 핸들바 (작은 영역만)
                Box(
                    modifier = Modifier
                        .width(34.dp)
                        .height(5.dp)
                        .background(Color(0xffc7c7c7), RoundedCornerShape(12.dp))
                        .draggable(
                            state = draggableState,
                            orientation = Orientation.Vertical,
                            onDragStopped = {
                                // 한 번의 드래그로 완전히 펼치거나 접기
                                when {
                                    dragOffset < -20f -> {
                                        // 위로 드래그 → 완전히 펼침 (stage 2)
                                        onStageChange(heights.size - 1)
                                    }
                                    dragOffset > 20f -> {
                                        // 아래로 드래그 → 완전히 접음 (stage 0)
                                        onStageChange(0)
                                    }
                                }
                                dragOffset = 0f
                            }
                        )
                )
            }

            // 컨텐츠 영역 (LazyColumn 등)
            Box(modifier = Modifier.weight(1f)) {
                content(currentStage)
            }
        }
    }
}
