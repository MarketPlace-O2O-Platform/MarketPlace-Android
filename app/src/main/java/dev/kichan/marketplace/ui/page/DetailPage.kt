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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun ImageSlider() {
    val images = listOf(
        R.drawable.burger,
        R.drawable.burger
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
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

    Spacer(modifier = Modifier.height(20.dp))

    Column(modifier = Modifier.padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "수제버거 브라더즈",
                fontWeight = FontWeight(600),
                lineHeight = 28.sp,
                fontSize = 19.sp,
                color = Color(0xFF121212),
                fontFamily = PretendardFamily
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
            text = "매일매일 신선한 소고기로 만든 꽉 찬 육즙을\n느낄 수 있는 00동 핫플, 수제버거 맛집",
            fontSize = 15.sp,
            lineHeight = 24.sp,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(500),
            color = Color(0xFF7D7D7D)
        )
    }
}

@Composable
fun CouponSlider() {
    val coupons = listOf(
        "스트리트 치킨 30% 할인" to "2025년 03월 21일까지",
        "버거킹 세트 20% 할인" to "2025년 04월 15일까지",
        "스타벅스 아메리카노 1+1" to "2025년 05월 10일까지"
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 20.dp), // 좌우 여백 추가
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(coupons) { _, coupon ->
            Box(
                modifier = Modifier
                    .width(335.dp)
                    .height(88.dp)
            ) {
                // 쿠폰 기본 배경 (subtract_black 이미지)
                Image(
                    painter = painterResource(id = R.drawable.subtract_black),
                    contentDescription = "쿠폰",
                    modifier = Modifier.fillMaxSize()
                )

                // 텍스트와 아이콘을 오버레이
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 텍스트 부분 (쿠폰 제목 + 날짜를 Row로 묶음)
                    Column(modifier = Modifier.weight(1f)) {
                        Row {
                            Text(
                                text = coupon.first,
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = PretendardFamily
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            Text(
                                text = coupon.second,
                                color = Color(0xFFFFFFFF),
                                fontSize = 14.sp,
                                fontWeight = FontWeight(400),
                                fontFamily = PretendardFamily,
                                lineHeight = 22.sp
                            )
                        }
                    }


                    // 쿠폰 받기 버튼 (아이콘 + 텍스트)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(55.dp)
                            .fillMaxHeight()
                            .padding(end=10.dp)

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.download), // 쿠폰 받기 아이콘
                            contentDescription = "쿠폰 받기",
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { /* 쿠폰 다운로드 기능 추가 */ }
                        )

                        Spacer(modifier = Modifier.height(11.dp))

                        Text(
                            text = "쿠폰받기",
                            fontSize = 12.sp,
                            lineHeight = 14.sp,
                            fontFamily = PretendardFamily,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        )
                    }
                }
            }
        }
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
                    .width(376.dp)
                    .height(8.dp)
                    .background(color = Color(0xFFEEEEEE))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "이벤트 쿠폰",
                fontWeight = FontWeight(600),
                lineHeight = 28.sp,
                color = Color(0xFF121212),
                fontSize = 18.sp,
                fontFamily = PretendardFamily,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            )

            CouponSlider()

            Text(
                text = "메인 메뉴(수제 버거) 주문 시, 쿠폰 적용 가능\n쿠폰 다운로드 시점으로부터 3일 이내로 미사용 시 소멸 예정",
                color = Color(0xFF7D7D7D),
                lineHeight = 19.5.sp,
                fontSize = 13.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(400),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "영업정보",
                fontSize = 14.sp,
                lineHeight = 28.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF121212),
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
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(69.dp, Alignment.Start)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            lineHeight = 18.2.sp,
            fontFamily = PretendardFamily,
            fontWeight = FontWeight(500),
            color = Color(0xFF868686),
            modifier = Modifier.width((75.dp))
        )
        Column {
            Text(
                text = value,
                fontSize = 13.sp,
                lineHeight = 22.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF5E5E5E),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPagePreview() {
    DetailPage()
}
