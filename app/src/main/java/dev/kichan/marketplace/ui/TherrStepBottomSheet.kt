package dev.kichan.marketplace.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThreeStepBottomSheet(
    modifier: Modifier = Modifier,
    sheetHeightDp: List<Dp>,
    swipeState: SwipeableState<Int>,
    overlayContent: @Composable () -> Unit,
    sheetContent: @Composable () -> Unit,
    mainContent: @Composable () -> Unit,
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val screenHeightPx = with(LocalDensity.current) {
        screenHeightDp.toPx()
    }
    val sheetHeightsPx = sheetHeightDp.map { with(LocalDensity.current) { it.toPx() } }
    val anchors = mapOf(
        (screenHeightPx - sheetHeightsPx[0]) to 0,
        (screenHeightPx - sheetHeightsPx[1]) to 1,
        (screenHeightPx - sheetHeightsPx[2]) to 2,
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        mainContent()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(swipeState.offset.value.dp)
        ) { overlayContent() }

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(x = 0, y = swipeState.offset.value.roundToInt())
                }
                .swipeable(
                    state = swipeState,
                    anchors = anchors,
                    orientation = Orientation.Vertical,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    resistance = null
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

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun ThreeStepBottomSheetPreview() {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val sheetHeightDp = listOf(60.dp, 200.dp, screenHeightDp - 50.dp)
    val swipeState = rememberSwipeableState(initialValue = 0)

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