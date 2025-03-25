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
import dev.kichan.marketplace.model.data.CouponResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CouponCard(coupon: CouponResponse, onClick: () -> Unit) {
    // 상태 값 설정
    val status = when {
        coupon.used -> "사용 완료"
        LocalDate.parse(coupon.deadLine.substring(0, 10)) < LocalDate.now() -> "기간 만료"
        else -> "사용 가능"
    }

    // 배경 이미지 설정
    val backgroundImage = when (status) {
        "사용 완료", "기간 만료" -> R.drawable.subtract2
        else -> R.drawable.component2
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() }
    ) {
        // 배경 이미지
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Coupon Background",
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 쿠폰 대표 이미지 (임시로 고정)
            Image(
                painter = painterResource(id = R.drawable.hair), // TODO: coupon.imageUrl이 있으면 변경
                contentDescription = "Coupon Image",
                modifier = Modifier.size(102.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 쿠폰 정보
            Column {
                Text(
                    text = coupon.couponName,
                    fontSize = 28.sp,
                    lineHeight = 42.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF121212),
                )
                Text(
                    text = coupon.description,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF121212),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${formatDate(coupon.deadLine)}",
                    fontSize = 13.sp,
                    lineHeight = 22.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
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

/**
 * 쿠폰 만료 날짜를 보기 좋은 형태로 변환
 */
fun formatDate(dateString: String): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val parsedDate = LocalDate.parse(dateString.substring(0, 10), formatter)
        parsedDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일까지"))
    } catch (e: Exception) {
        "날짜 오류"
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCouponCard() {
    Column {
        val sampleCoupon = CouponResponse(
            memberCouponId = 1,
            couponId = 101,
            couponName = "커피 1+1 쿠폰",
            description = "모든 매장에서 사용 가능",
            deadLine = "2025-03-30T23:59:59.999",
            used = false
        )

        CouponCard(coupon = sampleCoupon, onClick = {})
        Spacer(modifier = Modifier.height(16.dp))

        val expiredCoupon = sampleCoupon.copy(deadLine = "2024-03-10T23:59:59.999")
        CouponCard(coupon = expiredCoupon, onClick = {})

        Spacer(modifier = Modifier.height(16.dp))

        val usedCoupon = sampleCoupon.copy(used = true)
        CouponCard(coupon = usedCoupon, onClick = {})
    }
}
