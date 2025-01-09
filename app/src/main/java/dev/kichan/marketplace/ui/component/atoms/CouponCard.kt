package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
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
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun CouponCard(onClick: () -> Unit, status: String) {
    Row(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFFE0E0E0))
            .fillMaxWidth()
            .height(102.dp)
            .background(color = Color.White, shape = CutCornerShape(8.dp))
    ) {
        // 왼쪽 이미지 (정사각형)
        Image(
            painter = painterResource(id = R.drawable.hair), // 적절한 이미지로 교체
            contentDescription = "Coupon Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(102.dp) // 정사각형 크기
        )

        Spacer(modifier = Modifier.width(12.dp))

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
                    lineHeight = 42.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF121212)
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "붙임머리 할인",
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF121212)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp)) // Optional: Adjust spacing between text and icon
                Icon(
                    painter = painterResource(id = R.drawable.right), // Replace with your actual icon resource
                    contentDescription = "Icon Description", // Provide an appropriate description
                    modifier = Modifier.size(16.dp) // Adjust the size as needed
                )
            }

            Text(
                text = "2024년 10월 31일까지",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 22.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF545454)
                )
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // 상태에 따른 텍스트 및 색상 변경
        val backgroundColor = when (status) {
            "사용 가능" -> Color.Black
            "사용 완료" -> Color(0xFF7D7D7D) // 연회색
            "기간 만료" -> Color(0xFF7D7D7D) // 연회색
            else -> Color.White
        }

        val textColor = when (status) {
            "사용 가능" -> Color.White
            "사용 완료" -> Color.White // 흰색
            "기간 만료" -> Color.White // 흰색
            else -> Color.Black
        }

        Box(
            modifier = Modifier
                .clickable { onClick() }
                .width(80.dp)
                .fillMaxHeight()
                .background(color = backgroundColor, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = status,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCouponCard() {
    Column {
        CouponCard(onClick = {}, status = "사용 가능")
        Spacer(modifier = Modifier.height(16.dp))
        CouponCard(onClick = {}, status = "사용 완료")
        Spacer(modifier = Modifier.height(16.dp))
        CouponCard(onClick = {}, status = "기간 만료")
    }
}
