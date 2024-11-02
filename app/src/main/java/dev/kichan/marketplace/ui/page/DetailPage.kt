package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.R

@Composable
fun ImageSlider() {
    val images = listOf(
        R.drawable.brown,
        R.drawable.brown
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { imageRes ->
            val painter = painterResource(id = imageRes)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun DetailContent() {
    var isBookMark by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        // 가게 이름과 스크랩 아이콘을 가로로 정렬
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "콜드케이스 트리플 인하대점",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.bookmark),
                contentDescription = "Bookmark",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isBookMark = !isBookMark }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 가게 설명 텍스트
        Text(
            text = "믿고 즐기는 인하대 방탈출 맛집! 맛집! 맛집!\n스릴 넘치는 테마부터 낭만적인 꽃길 걷을 수 있는 곳!",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun DetailPage() {
    Column {
        ImageSlider()
        DetailContent()

        // 두꺼운 회색 바
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // "이벤트 쿠폰" 텍스트
        Text(
            text = "이벤트 쿠폰",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // 쿠폰 이미지
        Image(
            painter = painterResource(id = R.drawable.detailcoupon),
            contentDescription = "Detail Coupon",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        // "쿠폰 부가" 텍스트
        Text(
            text = "쿠폰 부가 설명 \n쿠폰 부가 설명",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        // 두 번째 회색 바 추가
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(Color.LightGray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPagePreview() {
    DetailPage()
}
