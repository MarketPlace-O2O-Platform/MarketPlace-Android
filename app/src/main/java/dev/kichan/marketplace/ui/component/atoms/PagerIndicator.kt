package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        modifier = modifier
    ) {
        val pageCount = pagerState.pageCount
        for (i in 0 until pageCount) {
            val isSelected = pagerState.currentPage == i
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .background(
                        color = if (isSelected) Color(0xff303030) else Color(0xffD9D9D9),
                        shape = CircleShape
                    )
            )
        }
    }
}