package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun CouponCard(onClick: () -> Unit, status: String) {
    // status 값에 따라 배경 이미지 변경
    val backgroundImage = when (status) {
        "사용 완료", "기간 만료" -> R.drawable.subtract2 // 사용 완료 또는 기간 만료 시 subtract2.png 사용
        else -> R.drawable.component // 기본 component.png 사용
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() }
    ) {
        // 배경 이미지 설정
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Coupon Background",
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 헤어 이미지
            Image(
                painter = painterResource(id = R.drawable.hair),
                contentDescription = "Hair Image",
                modifier = Modifier.size(102.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 쿠폰 정보
            Column {
                Text(
                    text = "70%",
                    fontSize = 28.sp,
                    lineHeight = 42.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF121212),
                )
                Text(
                    text = "붙임머리 할인",
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF121212),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "2024년 10월 31일까지",
                    fontSize = 13.sp,
                    lineHeight = 22.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF545454),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // 상태 표시 (사용 가능, 사용 완료, 기간 만료 등)
            Text(
                text = status,
                fontSize = 13.sp,
                lineHeight = 30.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 0.26.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 18.dp)
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
