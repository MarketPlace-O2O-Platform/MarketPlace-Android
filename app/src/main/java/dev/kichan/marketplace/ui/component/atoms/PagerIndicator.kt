package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        val pageCount = pagerState.pageCount
        for (i in 0 until pageCount) {
            val isSelected = pagerState.currentPage == i
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .background(
                        color = if (isSelected) Color(0xffffffff) else Color(0x4DFFFFFF),
                        shape = CircleShape
                    )
            )
        }
    }
}