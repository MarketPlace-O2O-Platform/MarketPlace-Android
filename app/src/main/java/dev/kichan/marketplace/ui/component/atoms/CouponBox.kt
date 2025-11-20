package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kichan.marketplace.ui.data.CouponBoxProps
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun CouponBox(
    modifier: Modifier = Modifier,
    couponBoxProps: CouponBoxProps,
) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(couponBoxProps.url)
                .crossfade(true)
                .build(),
            contentDescription = "Event Thumnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x4D000000))
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = couponBoxProps.subTitle,
                color = Color(0xffffffff),
                fontFamily = PretendardFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
            )
            Text(
                text = couponBoxProps.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventBox() {
    val couponBoxProps = CouponBoxProps(id = "ㅇ난영", title = "50% 할인권", subTitle = "싸다싸다", url = "image.kichan.dev/test.png", marketId = 1, onDownloadClick = {})
    CouponBox(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(1f / 1),
        couponBoxProps = couponBoxProps,
    )
}