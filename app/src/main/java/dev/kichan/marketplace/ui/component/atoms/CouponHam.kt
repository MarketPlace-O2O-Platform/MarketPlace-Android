package dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
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
import dev.kichan.marketplace.model.data.coupon.IssuedCouponRes
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun CouponUI(coupon : IssuedCouponRes) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)

    ) {
        // Background image (component.png)
        Image(
            painter = painterResource(id = R.drawable.component), // component.png
            contentDescription = "Coupon Background",
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .matchParentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hair image (hair.png)
            Image(
                painter = painterResource(id = R.drawable.hair), // hair.png
                contentDescription = "Hair Image",
                modifier = Modifier
                    .size(118.dp) // 크기를 적절히 조정
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Coupon details
            Column {
                Text(
                    text = "70%",
                    fontSize = 28.sp, // 글자 크기
                    lineHeight = 42.sp, // 줄 높이
                    fontFamily = PretendardFamily, // Pretendard 폰트 적용
                    fontWeight = FontWeight(600), // FontWeight 값 600을 SemiBold로 변경
                    color = Color(0xFF121212),
                )
                Text(
                    text = coupon.couponName,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF121212),

                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = coupon.deadLine,
                    fontSize = 13.sp,
                    lineHeight = 22.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF545454),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Right button or tag
            Text(
                text = "사용가능",
                fontSize = 13.sp,
                lineHeight = 30.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.26.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 20.dp)
            )
        }
    }
}