package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
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
import dev.kichan.marketplace.model.data.CouponResponse
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import dev.kichan.marketplace.ui.theme.PretendardFamily
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DetailCoupon(
    coupon: IssuedCouponRes,
    modifier: Modifier = Modifier,
    onClick: () -> Unit, // onClick 콜백 추가
) {
    Box(
        // CouponCard처럼 Box 전체에 클릭 영역 부여
        modifier = Modifier
            .width(335.dp)
            .height(88.dp)
            .clickable { onClick() } // 클릭 이벤트
    ) {
        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.coupon_black),
            contentDescription = "Coupon Background",
            modifier = Modifier
                .width(335.dp)
                .height(88.dp)
        )

        // 쿠폰명과 마감 기한 텍스트
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = coupon.couponName,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(600),
                fontFamily = PretendardFamily,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = formatDate2(coupon.deadLine),
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(400),
                color = Color.White
            )
        }
    }
}

// 날짜 포맷 함수
fun formatDate2(dateString: String): String {
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
fun PreviewDetailCoupon() {
    val sampleCoupon = IssuedCouponRes(
        couponId = 101,
        couponName = "스트리트 치킨 30% 할인",
        description = "매장에서 사용 가능",
        deadLine = "2025-03-21T23:59:59.999",
        isAvailable =true,
        isMemberIssued = true
    )
    DetailCoupon(
        coupon = sampleCoupon,
        onClick = {}
    )
}
