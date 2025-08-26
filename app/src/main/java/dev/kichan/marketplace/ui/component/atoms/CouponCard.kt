package dev.kichan.marketplace.ui.component.atoms

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun CouponCard(
    coupon: CouponResponse,
    onClick: () -> Unit
) {
    Log.d("CouponCard", "쿠폰 객체: $coupon")
    // 상태 텍스트
    val status = when {
        coupon.used -> "사용 완료"
        LocalDate.parse(coupon.deadLine.substring(0, 10)) < LocalDate.now() -> "기간 만료"
        else -> "사용 가능"
    }

    // 배경 이미지
    val backgroundImage = when (status) {
        "사용 완료", "기간 만료" -> R.drawable.subtract2
        else -> R.drawable.component
    }

    // 여기서 imageUrl 찍어보기
    val imageUrl = coupon.imageUrl ?: ""
    Log.d("CouponCard", "로딩할 이미지 URL: $imageUrl")

    // Coil로 로딩
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build()
        // placeholder = painterResource(R.drawable.placeholder),
        // error = painterResource(R.drawable.error_image),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() }
    ) {
        // 배경
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Coupon Background",
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 왼쪽 서버 이미지
            Image(
                painter = painter,
                contentDescription = "Coupon Image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 가운데 쿠폰 정보
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = coupon.couponName,
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PretendardFamily,
                    color = Color(0xFF121212),
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatDate(coupon.deadLine),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF545454),
                )
            }

            // 오른쪽 상태 표시
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = status,
                    fontSize = 13.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight(700),
                    color = Color.White,
                    fontFamily = PretendardFamily
                )
            }
        }
    }
}

// 날짜 포맷
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
        val sampleCoupon = CouponRes(
            couponId = 1,
            couponName = "커피 1+1 쿠폰",
            couponDescription = "모든 매장에서 사용 가능",
            deadLine = "2025-03-30T23:59:59.999",
            isHidden = false,
            isAvailable = true,
            isMemberIssued = false,
            couponType = "GIFT",
            marketId = 100L,
            marketName = "카페",
            address = "인천대학교",
            thumbnail = faker.company().logo(),
            couponCreatedAt = "2024-01-01T00:00:00.000",
            issuedCount = 100L
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
