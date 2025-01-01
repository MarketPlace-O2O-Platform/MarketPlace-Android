package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Preview(showBackground = true)
@Composable
fun RequestCard(
    //todo: 나중에 더 좋은 이름으로 변경
    //todo: state 추가
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.cafe), // 여기에 이미지 리소스 추가
                contentDescription = "Cafe Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color(0x6B000000))
            ) {}
        }

        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text(
                text = "‘콜드케이스 인하대점’ 할인을 받고 싶어요!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = PretendardFamily,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 좋아요 버튼과 수
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.FavoriteBorder, contentDescription = "Like")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "36")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // 모집 정보
                    Text(text = "공간 마감", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        text = "제휴 컨텐츠 중",
                        fontSize = 12.sp,
                        color = Color(0xff383838),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(7.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xffF3F0F0))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffe5f3ff),
                    contentColor = Color(0xff3da2ff),
                    disabledContainerColor = Color(0xfff4f4f4),
                    disabledContentColor = Color(0xffc2c6c9),
                ),
                shape = RoundedCornerShape(4.dp),
                enabled = true,
            ) {
                Text(
                    text = "360% 달성",
                    color = Color(0xff3da2ff),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}