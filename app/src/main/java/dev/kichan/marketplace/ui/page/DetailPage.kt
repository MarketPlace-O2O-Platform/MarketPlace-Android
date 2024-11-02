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

        // 첫 번째 회색 바
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
            text = "쿠폰 부가",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // 두 번째 회색 바
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // "영업 정보" 텍스트
        Text(
            text = "영업 정보",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // 영업 정보 내용
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            BusinessInfoRow("시간", "토일 11:00 - 23:00\n평일 12:00 - 23:00")
            BusinessInfoRow("휴무일", "매주 화요일")
            BusinessInfoRow("매장 전화번호", "032-000-0000")
            BusinessInfoRow("주소", "인천시 연수구 송도동 174-3 송도 트리플 스트리트 B동 2층 202,203호\n테크노파크역 2번 출구 도보 13분")

            // "주소 복사 | 길찾기" 텍스트 바로 아래 위치
            Text(
                text = "                         주소 복사 | 길찾기",
                color = Color(0xFF4B4B4B), // 색상 #4B4B4B로 설정
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun BusinessInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = Color(0xFF868686), // 연한 회색
            fontSize = 14.sp,
            modifier = Modifier.width(100.dp)
        )
        Column {
            Text(
                text = value,
                color = Color(0xFF5E5E5E), // 진한 회색
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPagePreview() {
    DetailPage()
}
