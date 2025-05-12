package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.NetworkModule
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import dev.kichan.marketplace.ui.faker
import dev.kichan.marketplace.ui.theme.PretendardFamily
import java.time.format.DateTimeFormatter

@Composable
fun CouponItem(
    coupon: IssuedCouponRes,
    modifier: Modifier = Modifier
) {
    val darkGrey = Color(0xFF303030)
    val dashColor = Color.LightGray

    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = NetworkModule.getImageModel(LocalContext.current, faker.company().logo()),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFEFEFEF))
                .padding(start = 16.dp, top = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "70%",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = coupon.couponName,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF121212),
                )
                Icon(Icons.Rounded.KeyboardArrowRight, contentDescription = null)
            }
            Text(
                text = coupon.deadLine,
                fontSize = 13.sp,
                lineHeight = 22.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF545454),
            )
        }

        Box(
            modifier = Modifier
                .width(72.dp)
                .fillMaxHeight()
                .background(darkGrey)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val dash = 8.dp.toPx()
                val gap = 4.dp.toPx()
                var y = 0f
                while (y < size.height) {
                    drawLine(
                        color = dashColor,
                        start = Offset(0f, y),
                        end = Offset(0f, y + dash),
                        strokeWidth = 2.dp.toPx()
                    )
                    y += dash + gap
                }
                val r = 12.dp.toPx()
                drawCircle(Color.White, radius = r, center = Offset(0f, 0f))
                drawCircle(Color.White, radius = r, center = Offset(0f, size.height))
            }
            Text(
                text = "사용 가능",
                fontSize = 13.sp,
                lineHeight = 30.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.26.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 20.dp)
            )
        }
    }
}

//@Composable
//fun CouponItem(coupon : IssuedCouponRes) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)
//
//    ) {
//        Row(
//            modifier = Modifier
//                .matchParentSize(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            AsyncImage(
//                model = NetworkModule.getImageModel(LocalContext.current, faker.company().logo()),
//                contentDescription = null,
//                modifier = Modifier
//                    .width(100.dp)
//                    .height(100.dp)
//                    .clip(RoundedCornerShape(4.dp)),
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.width(12.dp))
//            Column {
//                Text(
//                    text = "70%",
//                    fontSize = 28.sp, // 글자 크기
//                    lineHeight = 42.sp, // 줄 높이
//                    fontFamily = PretendardFamily, // Pretendard 폰트 적용
//                    fontWeight = FontWeight(600), // FontWeight 값 600을 SemiBold로 변경
//                    color = Color(0xFF121212),
//                )
//                Text(
//                    text = coupon.couponName,
//                    fontSize = 15.sp,
//                    lineHeight = 24.sp,
//                    fontFamily = PretendardFamily,
//                    fontWeight = FontWeight(600),
//                    color = Color(0xFF121212),
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = coupon.deadLine,
//                    fontSize = 13.sp,
//                    lineHeight = 22.sp,
//                    fontFamily = PretendardFamily,
//                    fontWeight = FontWeight(400),
//                    color = Color(0xFF545454),
//                )
//            }
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            // Right button or tag
//            Text(
//                text = "사용가능",
//                fontSize = 13.sp,
//                lineHeight = 30.sp,
//                fontFamily = PretendardFamily,
//                fontWeight = FontWeight(700),
//                color = Color(0xFFFFFFFF),
//                letterSpacing = 0.26.sp,
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//                    .padding(end = 20.dp)
//            )
//        }
//    }
//}