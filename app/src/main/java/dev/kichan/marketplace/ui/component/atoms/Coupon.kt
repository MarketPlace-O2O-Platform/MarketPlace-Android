package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.ui.theme.MarketPlaceTheme
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun Coupon() {
    Row(
        modifier = Modifier.background(color = Color(0xff303030))
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 28.dp, top = 18.dp, bottom = 18.dp)
        ) {
            Text(
                text = "메인 메뉴 30% 할인",
                color = Color(0xffffffff),
                fontFamily = PretendardFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "2024년 10월 31일까지",
                color = Color(0xffffffff),
                fontFamily = PretendardFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400
            )
        }

        Canvas(modifier = Modifier
            .width(16.dp)
            .height(100.dp)
        ) {
//            drawArc(
//                color = Color.White,
//                startAngle = 180f,
//                sweepAngle = 180f,
//                useCenter = true,
//            )

            val dashWidth = 20f // 대시 선의 높이
            val dashSpacing = 3f // 대시 선 사이의 간격
            val roadWidth = size.width / 5 // 도로의 너비 비율

            val startX = (size.width - roadWidth) / 2 // 도로의 중앙 정렬
            val endX = startX + roadWidth // 도로의 오른쪽 끝

            var currentY = 0f

            while (currentY < size.height) {
                // 대시 선 그리기
                drawLine(
                    color = Color.White,
                    start = Offset(startX, currentY),
                    end = Offset(endX, currentY),
                    strokeWidth = roadWidth / 8
                )
                // 다음 대시 위치 계산
                currentY += dashWidth + dashSpacing
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 22.dp, horizontal = 26.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color(0xffffffff)
            )
            Text(text = "쿠폰 받기", color = Color(0xffffffff))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponPreview() {
    MarketPlaceTheme {
        Coupon()
    }
}
