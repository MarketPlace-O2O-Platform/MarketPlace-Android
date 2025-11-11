package dev.kichan.marketplace.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.marketplace.R
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun RequestListScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        /** 뒤로가기 버튼 & 타이틀 **/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(end = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.left_black),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "요청하기",
                fontSize = 14.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Divider(color = Color(0xFFEEEEEE), thickness = 1.dp, modifier = Modifier.fillMaxWidth())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 40.dp)
        ) {
            /** 설명 텍스트 **/
            Text(
                text = "쿠폰 받고 싶은 매장을\n검색해주세요",
                fontSize = 24.sp,
                lineHeight = 36.sp,
                fontFamily = PretendardFamily,
                fontWeight = FontWeight(800),
                color = Color(0xFF333333),
            )

            Spacer(modifier = Modifier.height(20.dp))

            /** 검색 입력창 **/
            TextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        text = "인천대 떡볶이",
                        fontSize = 14.sp,
                        lineHeight = 22.4.sp,
                        fontFamily = PretendardFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000)
                    )
                },
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFF303030),
                        shape = RoundedCornerShape(size = 2.dp)
                    )
                    .width(335.dp)
                    .height(50.dp)
                    .background(
                        color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 2.dp)
                    )
            )
            Spacer(modifier = Modifier.height(20.dp))

            /** 검색 결과 리스트 **/
            RequestList()
        }
    }
}

@Composable
fun RequestList() {
    val searchResults = listOf(
        "인천대 떡볶이" to "인천 연수구 아카데미로 119",
        "인천대 진심떡볶이" to "인천 연수구 아카데미로 119",
        "인천대 무인떡볶이" to "인천 연수구 아카데미로 119",
        "인천대 무인떡볶이" to "인천 연수구 아카데미로 119"
    )
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(searchResults) { result ->
            Column(modifier = Modifier.padding(vertical = 0.dp)) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = result.first,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF333333),
                )
                Text(
                    text = result.second,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    fontFamily = PretendardFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF7D7D7D),
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Divider(
                color = Color(0xFFEEEEEE),
                thickness = 1.1.dp,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRequestListScreen() {
    RequestListScreen()
}
