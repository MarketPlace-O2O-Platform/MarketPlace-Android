package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() }
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
                    .size(118.dp) // Adjust size as needed
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Coupon details
            Column {
                Text(
                    text = "70%",
                    fontSize = 28.sp, // Text size
                    lineHeight = 42.sp, // Line height
                    fontFamily = PretendardFamily, // Pretendard font applied
                    fontWeight = FontWeight(600), // FontWeight value 600 = SemiBold
                    color = Color(0xFF121212),
                )
                Text(
                    text = "붙임머리 할인",
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF121212),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "2024년 10월 31일까지",
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
                text = status,
                fontSize = 13.sp,
                lineHeight = 30.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
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
