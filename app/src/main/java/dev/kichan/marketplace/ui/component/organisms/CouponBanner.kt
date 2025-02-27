package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.PagerCounter

@Composable
fun CouponBanner(modifier: Modifier = Modifier) {
    val bannerList = listOf(
        R.drawable.banner_2,
        R.drawable.banner_2,
        R.drawable.banner_2,
        R.drawable.banner_2,
        R.drawable.banner_2,
        R.drawable.banner_2,
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { bannerList.size })

    Box(
        Modifier
            .padding(horizontal = PAGE_HORIZONTAL_PADDING)
            .clip(shape = RoundedCornerShape(12.dp))
    ) {
        HorizontalPager(state = pagerState) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(335.0f / 360) // 배너 이미지 비율
            ) {
                Image(
                    painter = painterResource(id = bannerList[it]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        PagerCounter(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            pagerState = pagerState
        )
    }
}