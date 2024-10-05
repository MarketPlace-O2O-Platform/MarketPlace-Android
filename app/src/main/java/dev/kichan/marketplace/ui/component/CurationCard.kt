package dev.kichan.marketplace.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
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
fun CurationCard(
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

                // 모집 정보
                Text(text = "공간 마감 제휴 컨텐츠 중", fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(7.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color(0xffF3F0F0))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // todo : 버튼 수정
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {},
            ) {
                Text(
                    text = "360% 달성",
                    color = Color.White,
                )
            }
        }
    }
}