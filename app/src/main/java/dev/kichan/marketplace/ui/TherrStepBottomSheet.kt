package dev.kichan.marketplace.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.kichan.marketplace.ui.component.atoms.MarketListItem
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThreeStepBottomSheet(
    modifier: Modifier = Modifier,
    sheetHeightDp: List<Dp>,
    swipeState: AnchoredDraggableState<Int>,
    overlayContent: @Composable () -> Unit,
    sheetContent: @Composable () -> Unit,
    mainContent: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val screenHeightPx = with(density) { screenHeightDp.toPx() }
    val sheetHeightsPx = sheetHeightDp.map { with(density) { it.toPx() } }

    // 공식 문서 패턴: DraggableAnchors 빌더 사용
    val anchors = DraggableAnchors {
        0 at (screenHeightPx - sheetHeightsPx[0])
        1 at (screenHeightPx - sheetHeightsPx[1])
        2 at (screenHeightPx - sheetHeightsPx[2])
    }

    // 공식 문서 패턴: SideEffect에서 updateAnchors 호출 (레이아웃 의존적 anchors)
    SideEffect {
        swipeState.updateAnchors(anchors)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        mainContent()

        // offset이 초기화되지 않으면 overlayContent 표시 안함
        val currentOffset = swipeState.offset
        if (!currentOffset.isNaN()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(currentOffset.dp)
            ) { overlayContent() }
        }

        Box(
            modifier = Modifier
                .offset {
                    // offset이 초기화되지 않으면 0으로 처리
                    val offset = swipeState.offset
                    if (!offset.isNaN()) {
                        IntOffset(x = 0, y = offset.roundToInt())
                    } else {
                        IntOffset.Zero
                    }
                }
                .anchoredDraggable(
                    state = swipeState,
                    orientation = Orientation.Vertical
                )
                .fillMaxWidth()
                .height(sheetHeightDp.last())
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        ) {
            sheetContent()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun ThreeStepBottomSheetPreview() {
    val density = LocalDensity.current
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val sheetHeightDp = listOf(60.dp, 200.dp, screenHeightDp - 50.dp)

    // 프로젝트 버전의 AnchoredDraggableState 생성자 사용
    val swipeState = remember {
        AnchoredDraggableState(
            initialValue = 0,
            positionalThreshold = { distance -> distance * 0.3f },
            velocityThreshold = { with(density) { 125.dp.toPx() } },
            snapAnimationSpec = androidx.compose.animation.core.tween(),
            decayAnimationSpec = androidx.compose.animation.core.exponentialDecay()
        )
    }

    MarketPlaceTheme {
        ThreeStepBottomSheet(
            sheetHeightDp = sheetHeightDp,
            swipeState = swipeState,
            overlayContent = {},
            sheetContent = {
                Column {
                    for (i in 1..10) {
                        MarketListItem(
                            title = "매장 이름",
                            description = "대충 설명",
                            location = "송도",
                            imageUrl = faker.company().logo(),
                            isFavorite = false
                        ) { }
                    }
                }
            },
            mainContent = {
                Box(Modifier.fillMaxSize().background(Color.Blue)) {
                    Text("대충 지도")
                }
            }
        )
    }
}