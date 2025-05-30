package dev.kichan.marketplace.ui.component.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.ui.PAGE_HORIZONTAL_PADDING
import dev.kichan.marketplace.ui.component.atoms.SkeletonItem
import dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms.PagerCounter
import dev.kichan.marketplace.ui.theme.PretendardFamily

data class BannerItem(
    val title: String,
    val subTitle: String,
    val description: String,
    val imageUrl: String,
    val onClick: () -> Unit
)

@Composable
fun CouponBanner(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    bannerList: List<BannerItem>
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { bannerList.size })

    if (isLoading) {
        return SkeletonItem(
            Modifier
                .padding(horizontal = PAGE_HORIZONTAL_PADDING)
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .aspectRatio(335.0f / 360)
                .background(Color.Gray)
        )
    }

    Box(
        Modifier
            .padding(horizontal = PAGE_HORIZONTAL_PADDING)
            .clip(shape = RoundedCornerShape(12.dp))
    ) {
        HorizontalPager(state = pagerState) {
            val item = bannerList[it]
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(335.0f / 360) // 배너 이미지 비율
                    .clickable { item.onClick() }
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = NetworkModule.getImageModel(LocalContext.current, item.imageUrl),
                    contentDescription = "Banner Image",
                    contentScale = ContentScale.Crop,
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        text = item.subTitle,
                        color = Color.White,
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = item.title,
                        color = Color.White,
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = item.description,
                        color = Color.White,
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }
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