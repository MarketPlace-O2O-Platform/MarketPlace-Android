package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R

@Composable
fun CouponCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(102.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 0.dp) // 카드 내부 여백 추가
    ) {
        // 왼쪽 이미지 (정사각형)
        Image(
            painter = painterResource(id = R.drawable.cafe), // 적절한 이미지로 교체
            contentDescription = "Coupon Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(102.dp) // 정사각형 크기
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 텍스트 영역
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "70%",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF121212)
                )
            )

            Text(
                text = "붙임머리 할인",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF121212)
                )
            )

            Text(
                text = "2024년 10월 31일까지",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF545454)
                )
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // "사용가능" 영역
        Box(
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight()
                .background(color = Color(0xFF000000), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "사용가능",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCouponCard() {
    CouponCard()
}
