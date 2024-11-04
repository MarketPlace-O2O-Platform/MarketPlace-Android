package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme

@Composable
fun PagerCounter(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    Surface(
        modifier = modifier
            .background(color = Color(0x80000000), shape = RoundedCornerShape(50.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        color = Color.Transparent
    ) {
        Text(
            text = "${pagerState.currentPage + 1}/${pagerState.pageCount}",
            color = Color(0xffffffff)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PagerCounterPreview() {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 5 })

    MarketPlaceTheme {
        PagerCounter(
            pagerState = pagerState
        )
    }
}