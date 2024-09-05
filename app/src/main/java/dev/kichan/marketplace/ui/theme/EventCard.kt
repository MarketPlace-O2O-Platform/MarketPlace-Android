package dev.kichan.marketplace.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.marketplace.Event

@Composable
fun EventCard(event: Event) {
    var isBookMark by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(192.dp)
            .aspectRatio(1.0f)
    ) {
        Image(
            painter = painterResource(id = dev.kichan.marketplace.R.drawable.desert),
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
                imageVector = if (!isBookMark) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite,
                contentDescription = "Bookmark",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
            )

            Text(
                text = event.marketName,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventCard() {
    val event = Event(
        marketName = "콜드케이스 인하대점",
        eventName = "2인 디저트 이용권",
        defaultPrice = 50000,
        eventPrice = 29500
    )

    EventCard(event)
}
