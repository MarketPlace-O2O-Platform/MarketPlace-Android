package dev.kichan.marketplace.ui.component

import Carbon_bookmark
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items  // items를 사용하기 위해 추가
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.R
import dev.kichan.marketplace.model.data.event.Event

@Composable
fun EventCard(event: Event, imageResId: Int) {
    var isBookMark by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(192.dp)
            .aspectRatio(1.0f)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55000000))
        ) {
            Icon(
                imageVector = if (!isBookMark) Carbon_bookmark else Icons.Default.Favorite, // todo : 북마크 아이콘 수정
                contentDescription  = "Bookmark",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
                    .clickable { isBookMark = !isBookMark }
            )

            Text(
                text = event.eventName,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventCard() {
    val event1 = Event(
        marketName = "콜드케이스 인하대점",
        eventName = "2인 디저트 이용권",
        defaultPrice = 50000,
        eventPrice = 29500
    )

    val event2 = Event(
        marketName = "CHUNGDAMN",
        eventName = "2인 방탈출 이용권",
        defaultPrice = 50000,
        eventPrice = 29500
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOf(event1, event2)) { event ->
            val imageResId = when (event.eventName) {
                "2인 디저트 이용권" -> R.drawable.desert
                "2인 방탈출 이용권" -> R.drawable.roomex
                else -> R.drawable.cafe  // 기본 이미지로 대체
            }
            EventCard(event = event, imageResId = imageResId)
        }
    }
}
