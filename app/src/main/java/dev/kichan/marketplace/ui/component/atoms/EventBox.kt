package dev.kichan.marketplace.ui.component.dev.kichan.marketplace.ui.component.atoms

import Bookmark
import Carbon_bookmark
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kichan.marketplace.ui.theme.PretendardFamily

@Composable
fun EventBox(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    image: String,
) {
    var isBookMark by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "Event Thumnail",
            contentScale = ContentScale.Crop,
            modifier = modifier
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x4D000000))
        )

        Icon(
            imageVector = if(!isBookMark) Carbon_bookmark else Bookmark,
            contentDescription = "Bookmark",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clickable { isBookMark = !isBookMark }
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = subTitle,
                color = Color(0xffC7C7C7),
                fontFamily = PretendardFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventBox() {
    EventBox(
        modifier = Modifier.fillMaxWidth(0.7f).aspectRatio(1f/1),
        title = "아 싸다 쿠폰",
        subTitle = "사장님이 미쳤어요",
        image = ""
    )
}