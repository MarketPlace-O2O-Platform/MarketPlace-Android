package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
            .height(280.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(images) { _, imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(280.dp)
            )
        }
    }
}

@Composable
fun DetailContent() {
    var isBookMark by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
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

        Text(
            text = "믿고 즐기는 인하대 방탈출 맛집! 맛집! 맛집!\n스릴 넘치는 테마부터 낭만적인 꽃길 걷을 수 있는 곳!",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun KakaoMapSearchBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp)) // 둥근 모서리 설정
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.search), // search.png 불러오기
            contentDescription = null,
            modifier = Modifier
                .padding(start = 12.dp)
                .size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = buildAnnotatedString {
                append("카카오맵에서 ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("콜드케이스 트리플")
                }
                append(" 인하대점 검색")
            },
            fontSize = 14.sp,
            color = Color(0xFF545454), // 텍스트 색상 #545454로 설정
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun DetailPage() {
    Scaffold {
        Column(
            modifier = Modifier.padding(it).verticalScroll(rememberScrollState())
        ) {
            ImageSlider()
            DetailContent()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "이벤트 쿠폰",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.detailcoupon),
                contentDescription = "Detail Coupon",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Text(
                text = "쿠폰 부가 설명\n쿠폰 부가 설명",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "영업 정보",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                BusinessInfoRow("시간", "토일 11:00 - 23:00\n평일 12:00 - 23:00")
                BusinessInfoRow("휴무일", "매주 화요일")
                BusinessInfoRow("매장 전화번호", "032-000-0000")
                BusinessInfoRow("주소", "인천시 연수구 송도동 174-3 송도 트리플 스트리트 B동 2층 202,203호\n테크노파크역 2번 출구 도보 13분")

                Text(
                    text = "                         주소 복사 | 길찾기",
                    color = Color(0xFF4B4B4B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            KakaoMapSearchBox()
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
            color = Color(0xFF868686),
            fontSize = 14.sp,
            modifier = Modifier.width(100.dp)
        )
        Column {
            Text(
                text = value,
                color = Color(0xFF5E5E5E),
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
