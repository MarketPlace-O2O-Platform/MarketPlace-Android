package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.data.coupon.Coupon
import dev.kichan.marketplace.ui.theme.PretendardFamily
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun CouponCard(coupon: Coupon, imageUrl: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        //  가게 프로필
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.cafe),
                contentDescription = "Market Image",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Gray, shape = RoundedCornerShape(25.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.heightIn()
            ) {
                Text(
                    text = "살롱 505", // todo: 메장 이름 변경
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color(0xff121212),
                )

                Text(
                    text = "건강&뷰티", // todo: 카테고리, 위치 변경
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xff7d7d7d),
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        // 쿠폰 정보
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(width = 1.dp, color = Color(0xffE1E1E1), shape = RoundedCornerShape(8.dp))
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 쿠폰 가격
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Text(
                        text = coupon.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff121212)
                    )

                    Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)
                }
                Text(
                    text = "${coupon.deadline}까지", // Todo : 날짜 형식 변경
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 쿠폰에 체크
            Column(
                modifier = Modifier
                    .background(Color(0xFFF5F5F5))
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check Icon",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "받은쿠폰",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (coupon.description != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "이용안내",
                    fontFamily = PretendardFamily,
                    fontSize = 13.sp,
                    color = Color(0xff545454),
                    fontWeight = FontWeight(500),
                    modifier = Modifier
                        .background(color = Color(0xffeaeaea), shape = RoundedCornerShape(2.dp))
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = coupon.description,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(400),
                    fontSize = 13.sp,
                    color = Color(0xff545454),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCouponCard() {
    CouponCard(
        coupon = Coupon(
            id = 0,
            marketId = 0,
            name = "커트 2,000원 할인",
            description = "주말 사용 불가입니다.",
            deadline = LocalDate.of(2024, 10, 31),
            count = 0,
            isHidden = false,
            isDeleted = false,
            createdAt = LocalDate.now(),
            modifiedAt = null

        ),
        imageUrl = "https://via.placeholder.com/150"
    )
}
